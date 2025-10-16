package com.example.MSCafe.exception;

import com.example.MSCafe.dto.response.GenericResponseDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ChefNotFoundException.class)
    public ResponseEntity<GenericResponseDto> handleChefNotFoundException(ChefNotFoundException e) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();

        genericResponseDto.setSuccess(false);
        genericResponseDto.setMessage(e.getMessage());
        genericResponseDto.setDetail(null);

        return new ResponseEntity<> (genericResponseDto,HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<GenericResponseDto> handleDishNotFoundException(DishNotFoundException e) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();

        genericResponseDto.setSuccess(false);
        genericResponseDto.setMessage(e.getMessage());
        genericResponseDto.setDetail(null);

        return new ResponseEntity<>(genericResponseDto, HttpStatusCode.valueOf(404));

    }
}
