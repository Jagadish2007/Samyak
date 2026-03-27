package com.example.lms.controller;

import com.example.lms.model.User;
import com.example.lms.repository.UserRepository;
import com.example.lms.repository.EnrollmentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository repo;
    private final EnrollmentRepository enrollmentRepo;

    public UserController(UserRepository repo, EnrollmentRepository enrollmentRepo) {
        this.repo = repo;
        this.enrollmentRepo = enrollmentRepo;
    }

    // ✅ GET ALL USERS
    @GetMapping
    public List<User> getUsers() {
        return repo.findAll();
    }

    // ✅ DELETE USER (IMPORTANT)
    @DeleteMapping("/{id}")
    @Transactional
    public String deleteUser(@PathVariable Long id) {

        // 🔥 Step 1: Delete enrollments
        enrollmentRepo.deleteByUserId(id);

        // 🔥 Step 2: Delete user
        repo.deleteById(id);

        return "User deleted successfully";
    }
}