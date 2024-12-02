package com.example.hotel.controller;

import com.example.hotel.model.Announcement;
import com.example.hotel.model.Booking;
import com.example.hotel.service.AnnouncementService;
import com.example.hotel.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final BookingService bookingService;
    private final AnnouncementService announcementService; // Thêm service để lấy thông báo

    // Constructor injection for BookingService
    public HomeController(BookingService bookingService, AnnouncementService announcementService) {
        this.bookingService = bookingService;
        this.announcementService = announcementService;
    }

    @GetMapping("/")
    public String homePage(HttpSession session, Model model)
    {
        // Lấy danh sách các thông báo đang kích hoạt
        List<Announcement> announcements = announcementService.getActiveAnnouncements();
        model.addAttribute("announcements", announcements);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, HttpSession session) {
        if (session.getAttribute("role") != null && session.getAttribute("role").equals("ADMIN")) {
            List<Booking> bookings = bookingService.findAllBookings(); // Lấy tất cả booking
            model.addAttribute("bookings", bookings);

            // Thông tin phòng
            int totalDeluxe = 60;
            int totalCouple = 50;
            int totalFamily = 40;

            long bookedDeluxe = bookingService.countRoomsByType("DELUXE");
            long bookedCouple = bookingService.countRoomsByType("COUPLE");
            long bookedFamily = bookingService.countRoomsByType("FAMILY");

            int remainingDeluxe = totalDeluxe - (int) bookedDeluxe;
            int remainingCouple = totalCouple - (int) bookedCouple;
            int remainingFamily = totalFamily - (int) bookedFamily;

            // Thêm thông tin vào model
            model.addAttribute("totalDeluxe", totalDeluxe);
            model.addAttribute("remainingDeluxe", remainingDeluxe);
            model.addAttribute("totalCouple", totalCouple);
            model.addAttribute("remainingCouple", remainingCouple);
            model.addAttribute("totalFamily", totalFamily);
            model.addAttribute("remainingFamily", remainingFamily);

            return "admin"; // Trả về view admin.html
        }
        return "redirect:/"; // Nếu không phải ADMIN, chuyển hướng về trang chính
    }

}