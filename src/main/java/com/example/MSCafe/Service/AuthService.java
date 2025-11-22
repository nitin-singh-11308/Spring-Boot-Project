package com.example.MSCafe.Service;

import com.example.MSCafe.dto.request.EmailAndPasswordRequestDto;
import com.example.MSCafe.dto.request.SigninRequestDto;
import com.example.MSCafe.dto.response.GenericResponseDto;

public interface AuthService {
    GenericResponseDto signup(String authHeader, EmailAndPasswordRequestDto emailAndPasswordRequestDto);
    GenericResponseDto signin(SigninRequestDto signinRequestDto);
    GenericResponseDto resetPassword(String authHeader, EmailAndPasswordRequestDto emailAndPasswordRequestDto);
}

