package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
