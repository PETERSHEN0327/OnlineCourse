package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.model.Lecture;
import com.example.onlinecourse.repository.CourseRepository;
import com.example.onlinecourse.repository.LectureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
public class LectureController {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // ✅ 展示上传页面（根据 lecture ID）
    @GetMapping("/lecture/{id}/upload")
    public String showUploadForm(@PathVariable Long id, Model model) {
        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) return "redirect:/index";

        model.addAttribute("lecture", lecture);
        return "upload_material"; // JSP 页面名
    }

    // ✅ 处理上传提交
    @PostMapping("/lecture/{id}/upload")
    public String uploadMaterial(@PathVariable Long id,
                                 @RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) throws IOException {
        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) return "redirect:/index";

        if (!file.isEmpty()) {
            // 构建文件名和路径
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File destination = new File(uploadDir + "/" + filename);
            destination.getParentFile().mkdirs();
            file.transferTo(destination);

            // 设置文件访问路径并保存
            String fileUrl = "/uploads/" + filename;
            lecture.setMaterialUrl(fileUrl);
            lectureRepository.save(lecture);

            redirectAttributes.addFlashAttribute("success", "Upload successful.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Upload failed: No file selected.");
        }

        return "redirect:/index";
    }
}
