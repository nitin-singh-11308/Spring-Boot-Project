package com.example.MSCafe.Service;

import com.example.MSCafe.Repository.ChefRepository;
import com.example.MSCafe.dto.response.AccountResponseDto;
import com.example.MSCafe.model.Account;
import com.example.MSCafe.model.Chef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChefBalanceServiceImpl implements ChefBalanceService{
    @Autowired
    private ChefRepository chefRepository;

    @Override
    public AccountResponseDto addAmount(Long chefId, Double amount) {
        Chef chef = chefRepository.findById(chefId).orElse(null);

        Double existingAmount = chef.getAccount().getAmount();

        chef.getAccount().setAmount(existingAmount + amount);

        chefRepository.save(chef);

        Account account = chef.getAccount();

        AccountResponseDto accountResponseDto = new AccountResponseDto();
        accountResponseDto.setId(account.getId());
        accountResponseDto.setAmount(account.getAmount());
        accountResponseDto.setIsPaid(account.getIsPaid());
        accountResponseDto.setStatus(account.getStatus());

        return accountResponseDto;
    }

    @Override
    public AccountResponseDto deductAmount(Long chefId, Double amount) {
        return null;
    }
}
