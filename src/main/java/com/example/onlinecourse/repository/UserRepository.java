package com.example.onlinecourse.repository;

import com.example.onlinecourse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);  // 通过用户名查找用户
}
