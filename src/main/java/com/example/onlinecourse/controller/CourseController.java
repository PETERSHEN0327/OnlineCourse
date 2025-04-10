package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.Comment;
import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.repository.CommentRepository;
import com.example.onlinecourse.repository.CourseRepository;
import com.example.onlinecourse.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private CommentRepository commentRepository;

    // ✅ 展示课程详情页（课程内容 + 评论分页）
    @GetMapping("/course/{id}")
    public String coursePage(@PathVariable Long id,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             Model model) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) return "redirect:/index";

        model.addAttribute("course", course);
        model.addAttribute("lectures", lectureRepository.findByCourseId(id));

        Pageable pageable = PageRequest.of(page, 5, Sort.by("timestamp").descending());
        Page<Comment> commentPage = commentRepository.findByCourse(course, pageable);

        model.addAttribute("comments", commentPage.getContent());
        model.addAttribute("totalPages", commentPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalComments", commentPage.getTotalElements());

        return "course";
    }

    // ✅ 添加评论
    @PostMapping("/course/{id}/comment")
    public String addComment(@PathVariable Long id,
                             @RequestParam("content") String content,
                             Authentication auth) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) return "redirect:/index";

        Comment comment = new Comment();
        comment.setCourse(course);
        comment.setUsername(auth.getName());
        comment.setContent(content);
        comment.setTimestamp(LocalDateTime.now());

        commentRepository.save(comment);
        return "redirect:/course/" + id + "?success=Comment added";
    }

    // ✅ 删除评论（课程评论）
    @PostMapping("/course/{courseId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long courseId,
                                @PathVariable Long commentId,
                                Authentication auth) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null && (
                auth.getName().equals(comment.getUsername()) ||
                        auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"))
        )) {
            commentRepository.delete(comment);
        }
        return "redirect:/course/" + courseId;
    }

    // ✅ 编辑评论页面
    @GetMapping("/course/{courseId}/comment/{commentId}/edit")
    public String editCommentForm(@PathVariable Long courseId,
                                  @PathVariable Long commentId,
                                  Authentication auth,
                                  Model model) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null || !auth.getName().equals(comment.getUsername())) {
            return "redirect:/course/" + courseId;
        }
        model.addAttribute("comment", comment);
        model.addAttribute("courseId", courseId);
        return "edit_comment";
    }

    // ✅ 提交修改评论
    @PostMapping("/course/{courseId}/comment/{commentId}/edit")
    public String updateComment(@PathVariable Long courseId,
                                @PathVariable Long commentId,
                                @RequestParam("content") String content,
                                Authentication auth) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment != null && auth.getName().equals(comment.getUsername())) {
            comment.setContent(content);
            commentRepository.save(comment);
        }
        return "redirect:/course/" + courseId;
    }
}
