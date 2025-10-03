package com.example.MSCafe.Service;


import com.example.MSCafe.dto.response.AccountResponseDto;

public interface ChefBalanceService {
    AccountResponseDto addAmount(Long chefId, Double amount);
    AccountResponseDto deductAmount(Long chefId, Double amount);
}
