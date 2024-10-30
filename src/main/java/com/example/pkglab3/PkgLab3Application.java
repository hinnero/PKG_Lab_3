package com.example.pkglab3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PkgLab3Application {

    public static void main(String[] args) {
        SpringApplication.run(PkgLab3Application.class, args);
    }

}
