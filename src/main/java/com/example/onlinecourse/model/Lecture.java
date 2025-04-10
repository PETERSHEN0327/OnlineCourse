package com.example.onlinecourse.model;

import jakarta.persistence.*;

@Entity
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ 确保 ID 自动生成，避免手动冲突
    private Long id;

    @Column(nullable = false)
    private String title; // Lecture title

    private String materialUrl; // Material path (optional)

    // ✅ 与 Course 建立多对一关系，不能为空
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // ===== 构造函数 =====
    public Lecture() {}

    public Lecture(String title, String materialUrl, Course course) {
        this.title = title;
        this.materialUrl = materialUrl;
        this.course = course;
    }

    // ===== Getter & Setter =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
