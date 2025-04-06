package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.User;
import com.example.onlinecourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String profilePage(Model model) {
        // 获取当前登录用户的用户名
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // 查询用户信息
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "profile"; // profile.jsp
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User updatedUser, Model model) {
        // 获取当前用户
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);

        // 更新字段（用户名不变）
        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());

        // 更新密码（如果不是空的才更新）
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userRepository.save(user);

        model.addAttribute("user", user);
        model.addAttribute("message", "Profile updated successfully.");
        return "profile";
    }
}
