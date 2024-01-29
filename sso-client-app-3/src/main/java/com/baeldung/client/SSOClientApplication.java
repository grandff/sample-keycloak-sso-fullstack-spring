package com.baeldung.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.client")
public class SSOClientApplication {
    public static void main(String[] args) {
        System.out.println("SSOClientApplication is main!");
        SpringApplication.run(SSOClientApplication.class, args);
    }
}
