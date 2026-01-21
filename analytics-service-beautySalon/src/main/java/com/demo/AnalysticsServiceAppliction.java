package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.demo")
public class AnalysticsServiceAppliction {
    public static void main(String[] args) {
        SpringApplication.run(AnalysticsServiceAppliction.class, args);
    }
}