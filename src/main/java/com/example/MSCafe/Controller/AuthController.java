package com.example.MSCafe.Controller;

import com.example.MSCafe.Service.AuthService;
import com.example.MSCafe.Service.EmailService;
import com.example.MSCafe.dto.request.EmailAndPasswordRequestDto;
import com.example.MSCafe.dto.request.EmailOtpVerificationRequestDto;
import com.example.MSCafe.dto.request.EmailRequestDto;
import com.example.MSCafe.dto.request.SigninRequestDto;
import com.example.MSCafe.dto.response.GenericResponseDto;
import com.example.MSCafe.enums.OtpPurpose;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup/email/sent-otp")
    public ResponseEntity<GenericResponseDto>sendOtpToSignup (@Valid @RequestBody EmailRequestDto emailRequestDto){
        return new ResponseEntity<>(emailService.sendOtp(emailRequestDto, OtpPurpose.SIGNUP), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/signup/email/verify-otp")
    public ResponseEntity<GenericResponseDto>sendOtpToVerification (@Valid @RequestBody EmailOtpVerificationRequestDto emailOtpVerificationRequestDto){
        return new ResponseEntity<>(emailService.verifyOtp(emailOtpVerificationRequestDto, OtpPurpose.SIGNUP), HttpStatus.valueOf(201));
    }

    @PostMapping("/signup")
    public ResponseEntity<GenericResponseDto> signup(@RequestHeader("Authorization") String authHeader, @Valid @RequestBody EmailAndPasswordRequestDto emailAndPasswordRequestDto) {
        return new ResponseEntity<>(authService.signup(authHeader, emailAndPasswordRequestDto), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public  ResponseEntity<GenericResponseDto> signin(@Valid @RequestBody SigninRequestDto signinRequestDto) {
        return new ResponseEntity<>(authService.signin(signinRequestDto), HttpStatusCode.valueOf(200));
    }
}
