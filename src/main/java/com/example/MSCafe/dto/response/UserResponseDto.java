package com.example.MSCafe.dto.response;

import com.example.MSCafe.enums.UserRole;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private UserRole role;
    private String username;
}
