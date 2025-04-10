package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.Comment;
import com.example.onlinecourse.model.User;
import com.example.onlinecourse.model.Vote;
import com.example.onlinecourse.repository.CommentRepository;
import com.example.onlinecourse.repository.UserRepository;
import com.example.onlinecourse.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ 查看用户信息页面
    @GetMapping("/profile")
    public String profilePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "profile"; // profile.jsp
    }

    // ✅ 更新用户信息
    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute User updatedUser, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);

        user.setFullName(updatedUser.getFullName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userRepository.save(user);
        model.addAttribute("user", user);
        model.addAttribute("message", "Profile updated successfully.");
        return "profile";
    }

    // ✅ 用户评论历史页面
    @GetMapping("/profile/comments")
    public String viewComments(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<Comment> comments = commentRepository.findByUsernameOrderByTimestampDesc(username);
        model.addAttribute("comments", comments);
        return "profile_comments";
    }

    // ✅ 用户投票历史页面
    @GetMapping("/profile/votes")
    public String viewVotes(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        List<Vote> votes = voteRepository.findByUsername(username);
        model.addAttribute("votes", votes);
        return "profile_votes";
    }
}
