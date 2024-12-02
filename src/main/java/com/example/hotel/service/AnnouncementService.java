package com.example.hotel.service;

import com.example.hotel.model.Announcement;
import com.example.hotel.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<Announcement> getActiveAnnouncements() {
        List<Announcement> activeAnnouncements = announcementRepository.findByIsActiveTrue();
        System.out.println("Thông báo đang hiển thị: " + activeAnnouncements); // Debug log
        return activeAnnouncements;
    }

    public void saveAnnouncement(Announcement announcement) {
        if (announcement.getTitle() == null || announcement.getContent() == null) {
            throw new IllegalArgumentException("Tiêu đề và nội dung không được để trống.");
        }
        announcementRepository.save(announcement);
    }

    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }
}


