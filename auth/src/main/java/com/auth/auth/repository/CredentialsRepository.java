package com.auth.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.auth.entity.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Long>{
    public Optional<Credentials> findByUsername(String username);
}
