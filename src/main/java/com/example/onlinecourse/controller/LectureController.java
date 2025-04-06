package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.Lecture;
import com.example.onlinecourse.model.Comment;
import com.example.onlinecourse.repository.LectureRepository;
import com.example.onlinecourse.repository.CommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class LectureController {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 展示讲座详情页 + 所有评论
    @GetMapping("/lecture/{id}")
    public String lecturePage(@PathVariable("id") Long id, Model model,
                              @ModelAttribute("success") String success,
                              @ModelAttribute("error") String error) {
        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) return "redirect:/index";

        List<Comment> comments = commentRepository.findByLecture(lecture);

        model.addAttribute("lecture", lecture);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());

        if (success != null && !success.isEmpty()) {
            model.addAttribute("success", success);
        }
        if (error != null && !error.isEmpty()) {
            model.addAttribute("error", error);
        }

        return "lecture";
    }

    // 添加评论
    @PostMapping("/lecture/{id}/comment")
    public String addComment(@PathVariable("id") Long id,
                             @ModelAttribute("newComment") Comment comment) {
        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) return "redirect:/index";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        comment.setLecture(lecture);
        comment.setUsername(username);
        comment.setTimestamp(LocalDateTime.now());

        commentRepository.save(comment);
        return "redirect:/lecture/" + id;
    }

    // 显示上传讲义页面
    @GetMapping("/lecture/{id}/upload")
    public String showUploadForm(@PathVariable Long id, Model model) {
        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) return "redirect:/index";

        model.addAttribute("lecture", lecture);
        return "upload_material";
    }

    // 处理讲义上传（含提示）
    @PostMapping("/lecture/{id}/upload")
    public String uploadMaterial(@PathVariable Long id,
                                 @RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) throws IOException {
        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) return "redirect:/index";

        if (!file.isEmpty()) {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File destination = new File(uploadDir + "/" + filename);
            destination.getParentFile().mkdirs();
            file.transferTo(destination);

            String fileUrl = "/uploads/" + filename;
            lecture.setMaterialUrl(fileUrl);
            lectureRepository.save(lecture);

            redirectAttributes.addFlashAttribute("success", "Upload successful.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Upload failed: No file selected.");
        }

        return "redirect:/lecture/" + id;
    }

    // 编辑评论页面
    @GetMapping("/lecture/{lectureId}/comment/{commentId}/edit")
    public String editCommentForm(@PathVariable Long lectureId,
                                  @PathVariable Long commentId,
                                  Model model) {
        Lecture lecture = lectureRepository.findById(lectureId).orElse(null);
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (lecture == null || comment == null) return "redirect:/index";

        model.addAttribute("lecture", lecture);
        model.addAttribute("commentEdit", comment);
        return "edit_comment";
    }

    // 提交更新评论
    @PostMapping("/lecture/{lectureId}/comment/{commentId}/edit")
    public String updateComment(@PathVariable Long lectureId,
                                @PathVariable Long commentId,
                                @ModelAttribute Comment updatedComment) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) return "redirect:/index";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals(comment.getUsername())) return "redirect:/lecture/" + lectureId;

        comment.setContent(updatedComment.getContent());
        comment.setTimestamp(LocalDateTime.now());
        commentRepository.save(comment);

        return "redirect:/lecture/" + lectureId;
    }

    // 删除评论
    @GetMapping("/lecture/{lectureId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long lectureId,
                                @PathVariable Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) return "redirect:/index";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getName().equals(comment.getUsername())) return "redirect:/lecture/" + lectureId;

        commentRepository.delete(comment);
        return "redirect:/lecture/" + lectureId;
    }
}
