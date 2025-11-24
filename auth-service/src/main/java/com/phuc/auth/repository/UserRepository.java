package com.phuc.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phuc.auth.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Long userId);
    Optional<User> findByEmail(String email);
    Optional<User> findByAuth0Id(String auth0Id);
}

