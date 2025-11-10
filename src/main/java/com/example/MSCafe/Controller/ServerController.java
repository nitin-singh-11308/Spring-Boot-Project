package com.example.MSCafe.Controller;

import com.example.MSCafe.dto.response.ServerResponseDto;
import com.example.MSCafe.dto.response.ServerStatusResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api")
public class ServerController {

    private final Instant serverStartTime;
    private final String applicationName;

    public ServerController(@Value("${spring.application.name}") String applicationName) {
        this.serverStartTime = Instant.now();
        this.applicationName = applicationName;
    }

    @GetMapping
    public ResponseEntity<ServerStatusResponseDto> checkServerStatus() {
        ServerStatusResponseDto serverStatusResponseDto = new ServerStatusResponseDto(serverStartTime, applicationName);

        return new ResponseEntity<>(serverStatusResponseDto, HttpStatusCode.valueOf(200));
    }
}
