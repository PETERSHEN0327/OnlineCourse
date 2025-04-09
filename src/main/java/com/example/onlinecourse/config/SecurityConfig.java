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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ✅ 关闭 CSRF 和 frameOptions 以支持 H2 控制台
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.disable())


                // ✅ 权限控制配置
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/index", "/register", "/css/**", "/webjars/**", "/h2-console/**", "/WEB-INF/**").permitAll()  // 公开页面
                        .requestMatchers("/admin/**").hasAnyRole("TEACHER", "ADMIN")  // 管理员和教师权限
                        .requestMatchers("/login", "/WEB-INF/views/login.jsp").permitAll()
                        .anyRequest().authenticated()  // 其他页面需登录
                )

                // ✅ 登录配置
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/dologin")
                        .defaultSuccessUrl("/index", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // ✅ 登出配置
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )

                // ✅ 异常处理
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
