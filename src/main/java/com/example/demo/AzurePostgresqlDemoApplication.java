package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AzurePostgresqlDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AzurePostgresqlDemoApplication.class, args);

        System.out.println("Application started successfully.");
        
    }
}
