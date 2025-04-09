package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.User;
import com.example.onlinecourse.model.Lecture;
import com.example.onlinecourse.model.Comment;
import com.example.onlinecourse.model.Poll;
import com.example.onlinecourse.model.PollOption;

import com.example.onlinecourse.repository.UserRepository;
import com.example.onlinecourse.repository.LectureRepository;
import com.example.onlinecourse.repository.CommentRepository;
import com.example.onlinecourse.repository.PollRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PollRepository pollRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // ===================== 用户管理 =====================

    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin_users";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return "redirect:/admin/users";
        model.addAttribute("user", user);
        return "admin_user_edit";
    }

    @PostMapping("/user/edit")
    public String updateUser(@ModelAttribute User updatedUser) {
        User user = userRepository.findById(updatedUser.getId()).orElse(null);
        if (user != null) {
            user.setFullName(updatedUser.getFullName());
            user.setEmail(updatedUser.getEmail());
            user.setPhone(updatedUser.getPhone());
            user.setRole(updatedUser.getRole());
            userRepository.save(user);
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }

    // ===================== 讲座管理 =====================

    @GetMapping("/lecture/add")
    public String addLectureForm(Model model) {
        model.addAttribute("lecture", new Lecture());
        return "admin_lecture_add";
    }

    @PostMapping("/lecture/add")
    public String addLectureSubmit(@ModelAttribute Lecture lecture) {
        lectureRepository.save(lecture);
        return "redirect:/index";
    }

    @GetMapping("/lecture/delete/{id}")
    public String deleteLecture(@PathVariable Long id) {
        lectureRepository.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/lecture/{id}/material")
    public String lectureMaterialForm(@PathVariable Long id, Model model) {
        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) return "redirect:/index";
        model.addAttribute("lecture", lecture);
        return "admin_lecture_material";
    }

    @PostMapping("/lecture/{id}/material")
    public String uploadLectureMaterial(@PathVariable Long id,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) return "redirect:/index";

        if (!file.isEmpty()) {
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + "/" + filename);
            dest.getParentFile().mkdirs();
            file.transferTo(dest);

            lecture.setMaterialUrl("/uploads/" + filename);
            lectureRepository.save(lecture);
        }

        return "redirect:/lecture/" + id;
    }

    @GetMapping("/lecture/{id}/material/delete")
    public String deleteLectureMaterial(@PathVariable Long id) {
        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) return "redirect:/index";
        lecture.setMaterialUrl(null);
        lectureRepository.save(lecture);
        return "redirect:/lecture/" + id;
    }

    // ===================== 评论管理 =====================

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            Long lectureId = comment.getLecture().getId();
            commentRepository.delete(comment);
            return "redirect:/lecture/" + lectureId;
        }
        return "redirect:/index";
    }

    // ===================== 投票管理 =====================

    @GetMapping("/poll/add")
    public String addPollForm(Model model) {
        model.addAttribute("poll", new Poll());
        return "admin_poll_add";
    }

    @PostMapping("/poll/add")
    public String submitPoll(@ModelAttribute Poll poll,
                             @RequestParam("optionList") String optionList) {

        List<PollOption> options = List.of(optionList.split(",")).stream()
                .map(text -> {
                    PollOption option = new PollOption();
                    option.setOptionText(text.trim());
                    option.setPoll(poll);
                    return option;
                })
                .toList();

        poll.setOptions(options);
        pollRepository.save(poll);

        return "redirect:/index";
    }

    @GetMapping("/poll/delete/{id}")
    public String deletePoll(@PathVariable Long id) {
        pollRepository.deleteById(id);
        return "redirect:/index";
    }
}
