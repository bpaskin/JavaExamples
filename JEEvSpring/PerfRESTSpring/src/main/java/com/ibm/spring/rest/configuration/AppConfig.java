package com.ibm.spring.rest.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.ibm.spring.*")
public class AppConfig {
	
    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }
}
