package com.example.hotel.controller;

import com.example.hotel.model.Announcement;
import com.example.hotel.service.AnnouncementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/announcements")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    public String showAnnouncements(Model model) {
        model.addAttribute("announcements", announcementService.getActiveAnnouncements());
        return "admin-announcements";
    }

    @PostMapping("/create")
    public String createAnnouncement(@RequestParam String title, @RequestParam String content) {
        Announcement announcement = new Announcement();
        announcement.setTitle(title);
        announcement.setContent(content);
        announcement.setIsActive(true);
        announcementService.saveAnnouncement(announcement);
        return "redirect:/admin/announcements";
    }

    @PostMapping("/delete/{id}")
    public String deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return "redirect:/admin/announcements";
    }

}
