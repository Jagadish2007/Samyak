package com.example.lms.controller;

import com.example.lms.model.Enrollment;
import com.example.lms.model.User;
import com.example.lms.model.Course;
import com.example.lms.repository.EnrollmentRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin
public class ProgressController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<Map<String, Object>> getProgress() {

        List<Enrollment> enrollments = enrollmentRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Enrollment e : enrollments) {

            Map<String, Object> map = new HashMap<>();

            // ✅ Get user using ID
            User user = userRepository.findById(e.getUserId()).orElse(null);

            // ✅ Get course using ID
            Course course = courseRepository.findById(e.getCourseId()).orElse(null);

            map.put("student", user != null ? user.getName() : "Unknown");
            map.put("course", course != null ? course.getTitle() : "Unknown");

            // simple progress logic
            map.put("progress", 50);

            result.add(map);
        }

        return result;
    }
}