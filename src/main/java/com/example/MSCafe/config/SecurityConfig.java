package com.example.MSCafe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                // Disable CSRF to call All HTTP Methods (POST, PUT, PATCH, DELETE)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/dishes", "/api/dishes/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/dishes", "/api/dishes/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/user").permitAll()
                                .requestMatchers("/api", "/api/**").authenticated()
                                .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());
        return httpSecurity.build();
    }
}
