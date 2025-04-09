package com.example.onlinecourse.model;

import jakarta.persistence.*;

@Entity
public class PollOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String optionText; // ✅ 修改为更清晰的字段名

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    // === Getter & Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
