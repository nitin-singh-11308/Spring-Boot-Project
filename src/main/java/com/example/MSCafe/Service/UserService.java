package com.example.MSCafe.Service;

import com.example.MSCafe.dto.request.UserRequestDto;
import com.example.MSCafe.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser (UserRequestDto userRequestDto);
    UserResponseDto getUserDetails(Long id);
}
