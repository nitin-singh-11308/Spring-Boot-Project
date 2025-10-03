package com.example.MSCafe.dto.response;

import com.example.MSCafe.enums.AccountStatus;
import lombok.Data;

@Data
public class AccountResponseDto {
    private Long id;
    private Double amount;
    private Boolean isPaid;
    private AccountStatus status;
}
