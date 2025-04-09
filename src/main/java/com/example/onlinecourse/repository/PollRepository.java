package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {

}
