package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.Comment;
import com.example.onlinecourse.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByLecture(Lecture lecture);
}
