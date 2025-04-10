package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    // ✅ 根据 Course 实体查找
    List<Lecture> findByCourse(Course course);

    // ✅ 根据 Course 的 ID 查找（用于 Controller 中按 courseId 获取讲座）
    List<Lecture> findByCourseId(Long id);
}
