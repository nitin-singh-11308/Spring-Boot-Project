package com.example.MSCafe.dto.request;

import com.example.MSCafe.enums.DishCategory;
import lombok.Data;

@Data
public class DishRequestDto {
    private String name;
    private Double price;
    private DishCategory category;
}
