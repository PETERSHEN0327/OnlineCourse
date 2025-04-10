package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.Comment;
import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.model.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // ✅ 按课程查询
    List<Comment> findByCourseOrderByTimestampDesc(Course course);
    List<Comment> findByCourseAndUsername(Course course, String username);
    Page<Comment> findByCourse(Course course, Pageable pageable);

    // ✅ 按投票查询
    List<Comment> findByPollOrderByTimestampDesc(Poll poll);
    Page<Comment> findByPoll(Poll poll, Pageable pageable);
    List<Comment> findByPollAndUsername(Poll poll, String username);

    // ✅ 按用户名查询所有评论（新增）
    List<Comment> findByUsernameOrderByTimestampDesc(String username);
}
