package com.example.employeeManagement.repository;

import org.springframework.stereotype.Repository;

import com.example.employeeManagement.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}