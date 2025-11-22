package com.example.MSCafe.Service;

import com.example.MSCafe.Repository.UserRepository;
import com.example.MSCafe.constants.UserConstants;
import com.example.MSCafe.dto.request.EmailOtpVerificationRequestDto;
import com.example.MSCafe.dto.request.EmailRequestDto;
import com.example.MSCafe.dto.response.GenericResponseDto;
import com.example.MSCafe.enums.OtpPurpose;
import com.example.MSCafe.exception.InvalidOtpException;
import com.example.MSCafe.exception.UserNotFoundException;
import com.example.MSCafe.model.User;
import com.example.MSCafe.store.OtpStore;
import com.example.MSCafe.util.JwtUtil;
import com.example.MSCafe.util.OtpUtil;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
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

        String purposeMessage = (otpPurpose == OtpPurpose.SIGNUP) ? "Use the OTP below to complete your signin process."
                : "Use the OTP below to reset your password.";

        String htmlBody = """
                <html>
                    <body style= "font-family:Arial, san-serif; line-height: 1.6; color: #333;">
                        <h2 style="color: #$CAF50;">MSCafe</h2>
                        <p>Hello</p>
                        <p>%s</p>
                            <div style = "padding: 10px; background-color: #f3f3f3; border-radius; 5px; display: inline-block;">
                                <h3 style="margin:0; color: #333;">%s</h3>
                            </div>
                        <p>This OTP is Valid for <b>5 Minutes</b>. Please do not share it with anyone.</p>
                        <p>Thank you, <br/>MSCafe Team</p>
                    </body>
                </html>
                """.formatted(purposeMessage,otp);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlBody,true);

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

    @Override
    public GenericResponseDto sendSigninAlert(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User email: " +email+ " doesn't exist"));

        StringBuilder userFullName = new StringBuilder();
        userFullName.append((user.getFirstName() != null) ? user.getFirstName() : " " );
        userFullName.append(user.getLastName() != null ? " " + user.getLastName() : " ");

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        ZoneId zoneId = ZoneId.systemDefault();
        ZoneOffset zoneOffset = OffsetDateTime.now(zoneId).getOffset();

        String currentDate = currentDateTime.format(dateFormatter);
        String currentTime = currentDateTime.format(timeFormatter);
        String currentTimeZoneId = String.format("%s (UTC%s)", zoneId.getId(), zoneOffset);

        String time = currentDate + " " + currentTime + " " + currentTimeZoneId;

        String ipAddress = "Unknown";
        String device = "Unknown";

        String subject = "MSCafe - Signin Alert";

        String htmlBody = """
                   <html>
                        <body style = "font-family: Arial, sans-sarif; line-height: 1.6; color: #333; ">
                            <h2 style ="color: #4CAF50;">MSCafe</h2>
                            <p><strong>Sign-in Details:</strong></p>
                
                            <ul>
                                <li>Time: %s</li>
                                <li>IP Address: %s</li>
                                <li>Device: %s</li>
                            </ul>
                
                           <p>If this was you, no further action is needed. If you did not sign-in, please reset your password immediately.</p>
                           <p>Thank you, <br/>MSCafe Team</p>
                       </body>
                  </html>
                """.formatted(userFullName,time, ipAddress,device);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true enable HTML

            mailSender.send(mimeMessage);
        }catch (Exception e) {
            return GenericResponseDto.builder()
                    .success(false)
                    .message("Failed to sent OTP email. Please try again later.")
                    .build();

        }
        return GenericResponseDto.builder()
                .success(true)
                .message("OTP sent to "+email)
                .detail(Map.of("purpose", "Sign-in Alert"))
                .build();

    }

}
