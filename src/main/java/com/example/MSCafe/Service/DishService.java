package com.example.MSCafe.Service;

import com.example.MSCafe.dto.request.DishRequestDto;
import com.example.MSCafe.dto.response.DishResponseDto;
import com.example.MSCafe.dto.response.GenericResponseDto;
import com.example.MSCafe.enums.DishCategory;

import java.util.List;

public interface DishService {
    //CRUD
    DishResponseDto addDish(DishRequestDto dishRequestDto);
    DishResponseDto getDish(Long id);
    List<DishResponseDto> getAllDish();
    DishResponseDto updateDish(Long id, DishRequestDto dishRequestDto);
    GenericResponseDto removeDish(Long id);

    //custom finder method
    List<DishResponseDto> searchDish(String q, Double min, Double max);

    List<DishResponseDto> getAllDishByCategory (DishCategory dishCategory);
}
