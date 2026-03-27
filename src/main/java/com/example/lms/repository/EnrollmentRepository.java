package com.example.lms.repository;

import com.example.lms.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // ✅ Get all enrollments of a user
    List<Enrollment> findByUserId(Long userId);

    // ✅ Check if already enrolled (VERY IMPORTANT 🔥)
    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);

    // ✅ Prevent duplicate enrollment
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    void deleteByCourseId(Long courseId);
    void deleteByUserId(Long userId);
}
