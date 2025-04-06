package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.Vote;
import com.example.onlinecourse.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByUsername(String username);
    boolean existsByPollAndUsername(Poll poll, String username);
}
