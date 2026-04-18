package com.example.project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("api/auth/me")
    public Map<String, Object> getCurrentUser(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        if (authentication==null) {
            response.put("authenticated", false);
            return response;            
        }

        response.put("authenticated", true);
        response.put("username", authentication.getName());

        String role = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .findFirst()
        .orElse("ROLE_UNKNOWN");

        response.put("role", role);

        return response;
    }

    

}

    

