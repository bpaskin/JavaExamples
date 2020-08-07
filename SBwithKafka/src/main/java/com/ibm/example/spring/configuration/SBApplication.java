package com.ibm.example.spring.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ibm.example.spring")
public class SBApplication {

	public static void main(String[] args) {
		SpringApplication.run(SBApplication.class, args);
	}
}
