package com.example.MSCafe.Controller;

import com.example.MSCafe.dto.response.ServerResponseDto;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ServerController {
    @GetMapping
    public ResponseEntity<ServerResponseDto> serverStatus() {
        return new ResponseEntity<>(new ServerResponseDto("Server is Live"), HttpStatusCode.valueOf(200));
    }
}
