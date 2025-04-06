package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.model.Lecture;
import com.example.onlinecourse.model.Poll;
import com.example.onlinecourse.repository.CourseRepository;
import com.example.onlinecourse.repository.LectureRepository;
import com.example.onlinecourse.repository.PollRepository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private PollRepository pollRepository;

    // ✅ 处理根路径 /：未登录跳转到登录页，已登录跳转到 index
    @GetMapping("/")
    public String rootRedirect(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "index";  // 已登录，跳转到 /index
        }
        return "redirect:/login";  // 未登录，跳转到登录页
    }

    @GetMapping("/index")
    public String index(Model model) {
        // 读取课程信息（仅取第一个课程）
        List<Course> courses = courseRepository.findAll();
        String courseName = courses.isEmpty() ? "No Course Available" : courses.get(0).getName();

        // 加载讲座和投票
        List<Lecture> lectures = lectureRepository.findAll();
        List<Poll> polls = pollRepository.findAll();

        // 添加数据到 model 中
        model.addAttribute("courseName", courseName);
        model.addAttribute("lectures", lectures);
        model.addAttribute("polls", polls);

        // 返回 index.jsp 页面
        return "index";
    }
}
