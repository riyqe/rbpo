package com.example.labatri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.labatri.model")
public class LabatriApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabatriApplication.class, args);
    }
}
