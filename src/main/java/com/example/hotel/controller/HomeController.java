package com.example.hotel.controller;

import com.example.hotel.model.Booking;
import com.example.hotel.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
public class HomeController {

    private final BookingService bookingService;

    // Constructor injection for BookingService
    public HomeController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public String homePage(HttpSession session) {
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

    @GetMapping("/admin") // Giữ nguyên URL này
    public String adminPage(Model model, HttpSession session) {
        // Kiểm tra xem người dùng có phải là ADMIN hay không
        if (session.getAttribute("role") != null && session.getAttribute("role").equals("ADMIN")) {
            List<Booking> bookings = bookingService.findAllBookings(); // Tạo phương thức này trong BookingService
            model.addAttribute("bookings", bookings);
            return "admin"; // Tên view là admin.html
        }
        return "redirect:/"; // Nếu không phải ADMIN, chuyển hướng về trang chính
    }
}