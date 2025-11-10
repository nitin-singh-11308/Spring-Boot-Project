package com.example.MSCafe.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericResponseDto {
    private boolean success;
    private String message;
    private Object detail;
}
