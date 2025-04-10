package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.Vote;
import com.example.onlinecourse.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    // 根据用户名查询所有投票记录（用于用户投票历史）
    List<Vote> findByUsername(String username);

    // 判断是否对某投票投过票（防止重复投票）
    boolean existsByPollAndUsername(Poll poll, String username);

    // 查询某个投票中每个选项的得票数
    @Query("SELECT v.selectedOption, COUNT(v) FROM Vote v WHERE v.poll = :poll GROUP BY v.selectedOption")
    List<Object[]> countVotesByOption(@Param("poll") Poll poll);
}
