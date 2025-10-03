package com.example.MSCafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsCafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCafeApplication.class, args);
	}

}