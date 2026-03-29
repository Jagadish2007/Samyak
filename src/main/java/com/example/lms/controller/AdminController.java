package com.example.lms.controller;

import com.example.lms.repository.UserRepository;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.EnrollmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // ✅ Dashboard Stats API
    @GetMapping("/stats")
    public Map<String, Long> getStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("users", userRepository.count());
        stats.put("courses", courseRepository.count());
        stats.put("enrollments", enrollmentRepository.count());

        return stats;
    }
}