package com.rslakra.springbootsamples.jwtauthentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Home/Welcome controller for the JWT Authentication service
 */
@RestController
public class HomeController {

    /**
     * Welcome endpoint
     *
     * @return welcome message
     */
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to JWT Authentication Service");
        response.put("version", "1.0.0");
        response.put("status", "running");
        response.put("endpoints", Map.of(
            "register", "/api/v1/auth/register",
            "login", "/api/v1/auth/login",
            "h2", "/h2",
            "swagger-ui", "/swagger.html"
        ));
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     *
     * @return health status
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        return ResponseEntity.ok(response);
    }
}

