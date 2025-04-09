package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.Course;
import com.example.onlinecourse.model.Lecture;
import com.example.onlinecourse.model.Poll;
import com.example.onlinecourse.repository.CourseRepository;
import com.example.onlinecourse.repository.LectureRepository;
import com.example.onlinecourse.repository.PollRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private PollRepository pollRepository;

    // ✅ 根路径：根据是否登录跳转
    @GetMapping("/")
    public String rootRedirect(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index";  // 已登录跳转 index
        }
        return "redirect:/login";  // 未登录跳转登录页
    }

    // ✅ 索引页：显示所有课程（链接）、所有讲座和所有投票
    @GetMapping("/index")
    public String index(Model model) {
        List<Course> courses = courseRepository.findAll();
        List<Lecture> lectures = lectureRepository.findAll(); // 不按课程筛选
        List<Poll> polls = pollRepository.findAll();          // 全部投票

        model.addAttribute("courses", courses);
        model.addAttribute("lectures", lectures);
        model.addAttribute("polls", polls);
        return "index";
    }

    // ✅ 课程详情页：显示某课程的讲座（不显示投票）
    @GetMapping("/course/{id}")
    public String courseById(@PathVariable("id") Long courseId, Model model) {
        Course selected = courseRepository.findById(courseId).orElse(null);
        List<Course> allCourses = courseRepository.findAll();

        if (selected == null) {
            model.addAttribute("courseName", "Course Not Found");
            model.addAttribute("lectures", List.of());
        } else {
            model.addAttribute("courseName", selected.getName());
            model.addAttribute("lectures", lectureRepository.findByCourse(selected));
        }

        model.addAttribute("courses", allCourses);
        model.addAttribute("polls", pollRepository.findAll()); // 投票仍然显示（可选）
        return "index";
    }
}
