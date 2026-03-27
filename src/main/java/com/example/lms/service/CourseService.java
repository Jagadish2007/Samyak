package com.example.lms.service;

import com.example.lms.model.Course;
import com.example.lms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.lms.repository.EnrollmentRepository;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }
    
    @Transactional
    public void deleteCourse(Long id) {
    courseRepository.deleteById(id);
}

    @Transactional
    public void deleteEnrollmentsByCourseId(Long courseId) {
        enrollmentRepository.deleteByCourseId(courseId);  // ✅ FIXED
    }
}
