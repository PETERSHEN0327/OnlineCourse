package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.User;
import com.example.onlinecourse.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Transactional
@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User()); // 确保 JSP 页面能绑定表单
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result,
                               Model model, RedirectAttributes redirectAttributes) {

        // 校验失败，返回注册页并显示错误
        if (result.hasErrors()) {
            model.addAttribute("user", user); // 保留用户已填信息
            model.addAttribute("errors", result); // 传回错误信息
            return "register";
        }

        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("successMessage", "Registration successful. Please log in.");
        return "redirect:/login";
    }
}
