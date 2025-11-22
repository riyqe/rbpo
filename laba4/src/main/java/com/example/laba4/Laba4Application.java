package com.example.laba4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.laba4.model")
public class Laba4Application {

    public static void main(String[] args) {
        SpringApplication.run(Laba4Application.class, args);
    }
}
