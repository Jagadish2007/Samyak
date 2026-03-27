package com.example.lms.controller;

import com.example.lms.model.Enrollment;
import com.example.lms.repository.EnrollmentRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import jdk.javadoc.doclet.Reporter;

@RestController
@RequestMapping("/api/enroll")
@CrossOrigin("*")
public class EnrollmentController {

    private final EnrollmentRepository repo;

    public EnrollmentController(EnrollmentRepository repo) {
        this.repo = repo;
    }

    // ✅ FIXED (REMOVE extra path)
    @PostMapping
    public Enrollment enroll(@RequestBody Enrollment e) {
    System.out.println("ENROLL HIT: " + e.getName()); // 🔥 DEBUG
    return repo.save(e);
}
    // ✅ FIXED
    @GetMapping("/{userId}")
    public List<Enrollment> getEnrollments(@PathVariable Long userId) {
        return repo.findByUserId(userId);
    }

}