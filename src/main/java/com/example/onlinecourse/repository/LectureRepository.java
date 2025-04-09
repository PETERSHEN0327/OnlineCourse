package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    // ✅ 根据课程查找讲座
    List<Lecture> findByCourse(Course course);
}
