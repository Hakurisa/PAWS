package com.example.pawsdemo;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class PAWSdemo {

    public static void main(String[] args) {
        SpringApplication.run(PAWSdemo.class, args);
    }

    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
