package com.example.MSCafe.Service;

import com.example.MSCafe.Repository.UserRepository;
import com.example.MSCafe.constants.UserConstants;
import com.example.MSCafe.dto.request.EmailOtpVerificationRequestDto;
import com.example.MSCafe.dto.request.EmailRequestDto;
import com.example.MSCafe.dto.response.GenericResponseDto;
import com.example.MSCafe.enums.OtpPurpose;
import com.example.MSCafe.exception.InvalidOtpException;
import com.example.MSCafe.model.User;
import com.example.MSCafe.store.OtpStore;
import com.example.MSCafe.util.JwtUtil;
import com.example.MSCafe.util.OtpUtil;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public GenericResponseDto sendOtp(EmailRequestDto emailRequestDto, OtpPurpose otpPurpose) {
        String email = emailRequestDto.getEmail();
        User user = userRepository.findByEmail(email).orElse(null);

        if (otpPurpose == OtpPurpose.SIGNUP) {
            if (user != null && user.isActive()){
                return GenericResponseDto.builder()
                        .success(false)
                        .message("Email registered already "+ email)
                        .build();
            }
        } else if (otpPurpose == OtpPurpose.PASSWORD_RESET) {
                if ( user == null || user.isActive()) {
                    return GenericResponseDto.builder()
                            .success(false)
                            .message("Email doesn't exist or inactive account")
                            .build();
                }
          }

        String otp = OtpUtil.generateOtp();
        OtpStore.storeOtp(email,otp);

        String subject = "MS Cafe - " + (otpPurpose == OtpPurpose.SIGNUP ? "Signup OTP" : "Password Reset OTP");

        String purposeMessage = "<h1>Your OTP is" + otp + "</h1>";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(purposeMessage,true);

            mailSender.send(mimeMessage);
        }
            catch (Exception e) {
                    System.out.println(
                            "An exception occurred during mail sending: " + e.getMessage()
                    );
                    return GenericResponseDto.builder()
                            .success(false)
                            .message("Failed to send OTP email. Please try again later")
                            .build();
        }
        return GenericResponseDto.builder()
                .success(true)
                .message("OTP sent to " +email)
                .detail(Map.of("Purpose",otpPurpose.name()))
                .build();
    }

    @Override
    public GenericResponseDto verifyOtp(EmailOtpVerificationRequestDto emailOtpVerificationRequestDto, OtpPurpose otpPurpose) {
        String email = emailOtpVerificationRequestDto.getEmail();
        String otp = emailOtpVerificationRequestDto.getOtp();

        String storeOtp = OtpStore.getOtp(email);

        if (storeOtp != null && storeOtp.equals(otp)) {
            OtpStore.clearOtp(email);
            if (otpPurpose == OtpPurpose.SIGNUP){
                if (!userRepository.existsByEmail(email)) {
                    User user = User.builder()
                            .email(email)
                            .isEmailVerified(true)
                            .password(UserConstants.PASSWORD_NOT_SET)
                            .active(false)
                            .build();
                    userRepository.save(user);
                }

                String signupToken = jwtUtil.generateSignupToken(email);
                return GenericResponseDto.builder()
                        .success(true)
                        .message("OTP Verified")
                        .detail(Map.of("Signup Token", signupToken))
                        .build();

            } else if (otpPurpose == OtpPurpose.PASSWORD_RESET) {
                User user = userRepository.findByEmail(email).orElse(null);
                if (user != null && user.isActive()) {
                    String passwordResetToken = jwtUtil.generatePasswordResetToken(email);
                    return GenericResponseDto.builder()
                            .success(true)
                            .message("OTP Verified")
                            .detail(Map.of("passwordResetToken",passwordResetToken)).build();
                }
            }
        }

        throw new InvalidOtpException("Otp Verification Failed");
    }

}
