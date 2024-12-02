package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Tiêu đề thông báo
    private String content; // Nội dung thông báo
    private boolean isActive; // Chỉ định nếu thông báo đang hiển thị

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}

