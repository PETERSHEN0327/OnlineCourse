package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.Poll;
import com.example.onlinecourse.model.PollOption;
import com.example.onlinecourse.model.Vote;
import com.example.onlinecourse.repository.PollRepository;
import com.example.onlinecourse.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class VoteController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    // ✅ 显示投票页面，预加载选项，避免懒加载异常
    @GetMapping("/poll/{id}")
    public String votePage(@PathVariable Long id, Model model) {
        Poll poll = pollRepository.findById(id).orElse(null);
        if (poll == null) return "redirect:/index";

        // ✅ 提前加载选项集合（防止 LazyInitializationException）
        List<PollOption> options = poll.getOptions();
        options.size(); // 强制加载

        model.addAttribute("poll", poll);
        model.addAttribute("options", options);
        return "vote";
    }

    // ✅ 提交投票（含防重复投票逻辑）
    @PostMapping("/poll/{id}")
    public String submitVote(@PathVariable Long id,
                             @RequestParam("selectedOption") String selectedOption,
                             Authentication auth,
                             Model model) {
        Poll poll = pollRepository.findById(id).orElse(null);
        if (poll == null) return "redirect:/index";

        String username = auth.getName();

        // ✅ 可选：防止重复投票
        if (voteRepository.existsByPollAndUsername(poll, username)) {
            List<PollOption> options = poll.getOptions();
            options.size(); // 强制加载
            model.addAttribute("poll", poll);
            model.addAttribute("options", options);
            model.addAttribute("error", "You have already voted on this poll.");
            return "vote";
        }

        // ✅ 保存投票记录
        Vote vote = new Vote();
        vote.setUsername(username);
        vote.setSelectedOption(selectedOption);
        vote.setTimestamp(LocalDateTime.now());
        vote.setPoll(poll);

        voteRepository.save(vote);
        return "redirect:/profile/votes";
    }
}
