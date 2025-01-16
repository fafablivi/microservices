package com.auth.auth.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.auth.entity.Credentials;
import com.auth.auth.repository.CredentialsRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AuthService {

    @Autowired
    private CredentialsRepository credentialsRepository;
    private static final String SECRET_KEY = "enfaitilfaut32caracteresminimumpourquecamarchejecrois";
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Credentials saveCredentials(Credentials credentials) {
        String hashedPassword = passwordEncoder.encode(credentials.getPassword());
        credentials.setPassword(hashedPassword);
        return credentialsRepository.save(credentials);
    }

    public List<Credentials> getAllCredentials() {
        return credentialsRepository.findAll();
    }

    public boolean validateCredentials(String username, String password) {
        Optional<Credentials> credentials = credentialsRepository.findByUsername(username);
        if (credentials.isPresent()) {
            Credentials credential = credentials.get();
            return passwordEncoder.matches(password, credential.getPassword());
        }
        return false;
    }

    public String generateToken(String username, String password) {
        if (validateCredentials(username, password)) {
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                    .compact();
        }
        return null;
    }
}
