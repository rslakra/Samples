package com.rslakra.resourceserveraccess.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Spring Boot Resource Server Access");
        response.put("version", "3.5.7");
        response.put("status", "Running");
        response.put("endpoints", Map.of(
            "home", "/",
            "employees", "/user/getEmployees (ADMIN role required)"
        ));
        return response;
    }
}

