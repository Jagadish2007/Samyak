package com.example.lms;

import com.example.lms.model.Course;
import com.example.lms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void run(String... args) throws Exception {
        if (courseRepository.count() == 0) {
            Course course1 = new Course();
            course1.setTitle("Introduction to Java Programming");
            course1.setDescription("Learn the fundamentals of Java programming including OOP concepts, data structures, and algorithms.");
            courseRepository.save(course1);

            Course course2 = new Course();
            course2.setTitle("Web Development with Spring Boot");
            course2.setDescription("Master Spring Boot framework to build modern web applications with REST APIs and database integration.");
            courseRepository.save(course2);

            Course course3 = new Course();
            course3.setTitle("Database Management Systems");
            course3.setDescription("Understand relational databases, SQL queries, normalization, and database design principles.");
            courseRepository.save(course3);

            System.out.println("Sample courses added to database!");
        }
    }
}
