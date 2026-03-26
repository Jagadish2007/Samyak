package com.example.lms.controller;

import com.example.lms.model.Enrollment;
import com.example.lms.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<?> enroll(@RequestBody Enrollment enrollment) {
        try {
            Enrollment createdEnrollment = enrollmentService.enroll(enrollment);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Enrollment successful");
            response.put("enrollment", createdEnrollment);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/enrollments/{userId}")
    public ResponseEntity<?> getEnrollmentsByUserId(@PathVariable Long userId) {
        try {
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByUserId(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("enrollments", enrollments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
