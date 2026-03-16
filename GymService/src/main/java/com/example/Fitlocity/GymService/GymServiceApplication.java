package com.fitlocity.gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fitlocity.gym", "com.example.Fitlocity.GymService"})
public class GymServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymServiceApplication.class, args);
    }
}