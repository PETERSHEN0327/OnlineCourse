package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.User;
import com.example.onlinecourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.crypto.password.PasswordEncoder;


import jakarta.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerPage() {
        return "register";  // 显示注册页面
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result,
                               Model model, RedirectAttributes redirectAttributes) {
        // 校验失败
        if (result.hasErrors()) {
            return "register";
        }

        // 用户名已存在
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "用户名已存在！");
            return "register";
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 保存用户
        userRepository.save(user);

        // 携带注册成功提示信息，重定向到登录页
        redirectAttributes.addFlashAttribute("successMessage", "注册成功，请登录！");
        return "redirect:/";
    }
}
