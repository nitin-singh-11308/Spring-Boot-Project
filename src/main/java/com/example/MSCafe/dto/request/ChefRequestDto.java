package com.example.MSCafe.dto.request;

import com.example.MSCafe.enums.Cuisine;
import lombok.Data;

@Data
public class ChefRequestDto {
    private String name;
    private Double experience;
    private Cuisine cuisine;
}
