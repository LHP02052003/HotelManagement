package com.example.hotel.repository;

import com.example.hotel.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByIsActiveTrue(); // Lấy các thông báo đang hiển thị
}
