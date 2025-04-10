package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.Poll;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PollRepository extends JpaRepository<Poll, Long> {

    // 自动加载 options 避免 LazyInitializationException
    @EntityGraph(attributePaths = "options")
    Optional<Poll> findWithOptionsById(Long id);
}
