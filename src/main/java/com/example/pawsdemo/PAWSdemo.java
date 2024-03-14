package com.example.pawsdemo;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PAWSdemo {

    public static void main(String[] args) {
        SpringApplication.run(PAWSdemo.class, args);
    }

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

}
