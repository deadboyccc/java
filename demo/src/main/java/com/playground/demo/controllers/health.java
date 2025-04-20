package com.playground.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class health {

    @GetMapping("/health")
    public String healthCheck() {
        return "Application is running!";
    }
}
