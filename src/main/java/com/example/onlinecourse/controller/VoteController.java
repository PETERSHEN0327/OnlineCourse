package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.Poll;
import com.example.onlinecourse.model.Vote;
import com.example.onlinecourse.repository.PollRepository;
import com.example.onlinecourse.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class VoteController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    // 显示投票页面
    @GetMapping("/poll/{id}")
    public String votePage(@PathVariable Long id, Model model) {
        Poll poll = pollRepository.findById(id).orElse(null);
        if (poll == null) return "redirect:/index";

        model.addAttribute("poll", poll);
        return "vote";
    }

    // 提交投票
    @PostMapping("/poll/{id}")
    public String submitVote(@PathVariable Long id,
                             @RequestParam("selectedOption") String selectedOption,
                             Authentication auth,
                             Model model) {
        Poll poll = pollRepository.findById(id).orElse(null);
        if (poll == null) return "redirect:/index";

        String username = auth.getName();

        // 防止重复投票（可选）
        if (voteRepository.existsByPollAndUsername(poll, username)) {
            model.addAttribute("error", "You have already voted on this poll.");
            model.addAttribute("poll", poll);
            return "vote";
        }

        Vote vote = new Vote();
        vote.setUsername(username);
        vote.setSelectedOption(selectedOption);
        vote.setTimestamp(LocalDateTime.now());
        vote.setPoll(poll);

        voteRepository.save(vote);
        return "redirect:/profile/votes";
    }
}
