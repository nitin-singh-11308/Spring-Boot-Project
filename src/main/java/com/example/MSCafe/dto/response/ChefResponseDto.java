package com.example.MSCafe.dto.response;

import com.example.MSCafe.Model.Account;
import com.example.MSCafe.enums.Cuisine;
import lombok.Data;

@Data
public class ChefResponseDto {
    private Long id;
    private String name;
    private Double experience;
    private Cuisine cuisine;
    private Account account;

}
