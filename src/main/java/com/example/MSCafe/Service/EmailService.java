package com.example.MSCafe.Service;

import com.example.MSCafe.dto.request.EmailOtpVerificationRequestDto;
import com.example.MSCafe.dto.request.EmailRequestDto;
import com.example.MSCafe.dto.response.GenericResponseDto;
import com.example.MSCafe.enums.OtpPurpose;

public interface EmailService {
    GenericResponseDto sendOtp(EmailRequestDto emailRequestDto, OtpPurpose otpPurpose);
    GenericResponseDto verifyOtp(EmailOtpVerificationRequestDto emailOtpVerificationRequestDto, OtpPurpose otpPurpose);
}
