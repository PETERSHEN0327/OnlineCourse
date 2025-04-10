package com.example.onlinecourse.controller;

import com.example.onlinecourse.model.Comment;
import com.example.onlinecourse.model.Poll;
import com.example.onlinecourse.model.PollOption;
import com.example.onlinecourse.model.Vote;
import com.example.onlinecourse.repository.CommentRepository;
import com.example.onlinecourse.repository.PollRepository;
import com.example.onlinecourse.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class VoteController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private CommentRepository commentRepository;

    // ✅ 显示投票详情页面（含评论分页）
    @GetMapping("/poll/{id}")
    public String votePage(@PathVariable Long id,
                           @RequestParam(defaultValue = "0") int page,
                           Model model,
                           Authentication auth) {
        Poll poll = pollRepository.findById(id).orElse(null);
        if (poll == null) return "redirect:/index";

        List<PollOption> options = poll.getOptions();
        options.size(); // 初始化懒加载

        Map<String, Long> voteCounts = new HashMap<>();
        List<Object[]> countList = voteRepository.countVotesByOption(poll);
        for (Object[] row : countList) {
            voteCounts.put((String) row[0], (Long) row[1]);
        }

        Pageable pageable = PageRequest.of(page, 5, Sort.by("timestamp").descending());
        Page<Comment> commentPage = commentRepository.findByPoll(poll, pageable);

        model.addAttribute("poll", poll);
        model.addAttribute("options", options);
        model.addAttribute("voteCounts", voteCounts);
        model.addAttribute("comments", commentPage.getContent());
        model.addAttribute("currentPage", commentPage.getNumber());
        model.addAttribute("totalPages", commentPage.getTotalPages());
        model.addAttribute("totalComments", commentPage.getTotalElements());
        model.addAttribute("currentUsername", auth.getName());
        return "vote";
    }

    // ✅ 提交投票
    @PostMapping("/poll/{id}")
    @Transactional
    public String submitVote(@PathVariable Long id,
                             @RequestParam("selectedOptionId") Long selectedOptionId,
                             Authentication auth,
                             Model model) {
        Poll poll = pollRepository.findById(id).orElse(null);
        if (poll == null) return "redirect:/index";

        String username = auth.getName();

        if (voteRepository.existsByPollAndUsername(poll, username)) {
            model.addAttribute("poll", poll);
            model.addAttribute("options", poll.getOptions());
            model.addAttribute("error", "You have already voted on this poll.");
            return "vote";
        }

        PollOption selectedOption = poll.getOptions().stream()
                .filter(opt -> opt.getId().equals(selectedOptionId))
                .findFirst().orElse(null);

        if (selectedOption == null) {
            model.addAttribute("poll", poll);
            model.addAttribute("options", poll.getOptions());
            model.addAttribute("error", "Invalid option selected.");
            return "vote";
        }

        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setUsername(username);
        vote.setTimestamp(LocalDateTime.now());
        vote.setSelectedOption(selectedOption.getOptionText());

        voteRepository.save(vote);
        return "redirect:/poll/" + id + "?success=Vote submitted";
    }

    // ✅ 添加评论
    @PostMapping("/poll/{pollId}/comment")
    public String addComment(@PathVariable Long pollId,
                             @RequestParam("content") String content,
                             Authentication auth) {
        Poll poll = pollRepository.findById(pollId).orElse(null);
        if (poll == null) return "redirect:/index";

        Comment comment = new Comment();
        comment.setPoll(poll);
        comment.setUsername(auth.getName());
        comment.setContent(content);
        comment.setTimestamp(LocalDateTime.now());

        commentRepository.save(comment);
        return "redirect:/poll/" + pollId + "?success=Comment posted";
    }

    // ✅ 显示评论编辑表单
    @GetMapping("/poll/{pollId}/comment/{commentId}/edit")
    public String editCommentForm(@PathVariable Long pollId,
                                  @PathVariable Long commentId,
                                  Model model,
                                  Authentication auth) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null || !comment.getUsername().equals(auth.getName())) {
            return "redirect:/poll/" + pollId;
        }

        model.addAttribute("pollId", pollId); // ✅ 添加 pollId 用于 JSP
        model.addAttribute("comment", comment); // ✅ 用 comment，不要用 commentEdit
        return "edit_comment";
    }

    // ✅ 提交评论修改
    @PostMapping("/poll/{pollId}/comment/{commentId}/edit")
    public String updateComment(@PathVariable Long pollId,
                                @PathVariable Long commentId,
                                @RequestParam("content") String content,
                                Authentication auth) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null || !comment.getUsername().equals(auth.getName())) {
            return "redirect:/poll/" + pollId + "?error=Unauthorized";
        }

        comment.setContent(content);
        comment.setTimestamp(LocalDateTime.now());
        commentRepository.save(comment);
        return "redirect:/poll/" + pollId + "?success=Comment updated";
    }

    // ✅ 删除评论（本人或教师）
    @PostMapping("/poll/{pollId}/comment/{commentId}/delete")
    @Transactional
    public String deleteComment(@PathVariable Long pollId,
                                @PathVariable Long commentId,
                                Authentication auth) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null || comment.getPoll() == null || !comment.getPoll().getId().equals(pollId)) {
            return "redirect:/poll/" + pollId;
        }

        String username = auth.getName();
        boolean isTeacher = auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_TEACHER"));

        if (username.equals(comment.getUsername()) || isTeacher) {
            commentRepository.delete(comment);
        }

        return "redirect:/poll/" + pollId + "?success=Comment deleted";
    }
}
