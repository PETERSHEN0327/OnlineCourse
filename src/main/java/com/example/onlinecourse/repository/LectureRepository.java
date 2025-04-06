package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
