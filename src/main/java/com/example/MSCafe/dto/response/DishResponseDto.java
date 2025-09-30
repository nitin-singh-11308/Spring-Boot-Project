package com.example.MSCafe.dto.response;

import com.example.MSCafe.enums.DishCategory;
import lombok.Data;

@Data
public class DishResponseDto {
    private Long id;
    private String name;
    private Double price;
    private DishCategory category;
}
