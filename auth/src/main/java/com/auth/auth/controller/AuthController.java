package com.auth.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.auth.entity.Credentials;
import com.auth.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public ResponseEntity<List<Credentials>> getAllCredentials() {
        return ResponseEntity.ok(authService.getAllCredentials());
    }

    @PostMapping("/register")
    public ResponseEntity<Credentials> createStudent(@RequestBody Credentials credentials) {
        return ResponseEntity.ok(authService.saveCredentials(credentials));
    }

    @PostMapping("/token")
    public ResponseEntity<String> generateToken(@RequestBody Credentials credentials) {
        String token = authService.generateToken(credentials.getUsername(), credentials.getPassword());
        if (token != null) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

}
