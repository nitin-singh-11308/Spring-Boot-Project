package com.example.MSCafe.Controller;

import com.example.MSCafe.Service.UserService;
import com.example.MSCafe.dto.request.UserRequestDto;
import com.example.MSCafe.dto.response.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto userRequestDto) {
       return new ResponseEntity<>(userService.registerUser(userRequestDto), HttpStatus.CREATED);
    }
}
