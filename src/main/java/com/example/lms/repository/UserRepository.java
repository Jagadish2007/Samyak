package com.example.lms.repository;

import com.example.lms.model.User;
import com.example.lms.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    // NEW: Get all students only
    List<User> findByRole(Role role);
}