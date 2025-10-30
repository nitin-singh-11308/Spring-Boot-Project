package com.example.MSCafe.dto.request;

import com.example.MSCafe.enums.UserRole;
import lombok.Data;

@Data
public class UserRequestDto {
    private UserRole role;
    private String username;
    private String password;
}
