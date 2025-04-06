package com.example.onlinecourse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Spring Security 配置
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/register", "/css/**", "/webjars/**").permitAll()  // 公开的页面
                        .requestMatchers("/admin/**").hasAnyRole("TEACHER", "ADMIN")  // 管理员和教师角色
                        .requestMatchers("/login", "/WEB-INF/views/login.jsp").permitAll()
                        .anyRequest().authenticated()  // 其他页面需要认证
                )
                .formLogin(login -> login
                        .loginPage("/login")  // 自定义登录页面
                        .loginProcessingUrl("/dologin")  // 登录表单提交路径
                        .defaultSuccessUrl("/index", true)  // 登录成功后跳转首页
                        .failureUrl("/login?error=true")  // 登录失败跳转
                        .permitAll()  // 允许所有用户访问登录页面
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // 登出路径
                        .logoutSuccessUrl("/login?logout=true")  // 登出成功跳转登录页面
                        .permitAll()  // 允许所有用户访问登出
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")  // 定义访问被拒绝页面
                );

        return http.build();
    }

    // 密码加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 使用 BCrypt 加密密码
    }
}
