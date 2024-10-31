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
            int totalRoomType1 = 40;
            int totalRoomType2 = 50;
            int totalRoomType3 = 60;

            long bookedRoomType1 = bookingService.countRoomsByType(1);
            long bookedRoomType2 = bookingService.countRoomsByType(2);
            long bookedRoomType3 = bookingService.countRoomsByType(3);

            int remainingRoomType1 = totalRoomType1 - (int) bookedRoomType1;
            int remainingRoomType2 = totalRoomType2 - (int) bookedRoomType2;
            int remainingRoomType3 = totalRoomType3 - (int) bookedRoomType3;

            // Thêm thông tin vào model
            model.addAttribute("totalRoomType1", totalRoomType1);
            model.addAttribute("remainingRoomType1", remainingRoomType1);
            model.addAttribute("totalRoomType2", totalRoomType2);
            model.addAttribute("remainingRoomType2", remainingRoomType2);
            model.addAttribute("totalRoomType3", totalRoomType3);
            model.addAttribute("remainingRoomType3", remainingRoomType3);
            return "admin"; // Tên view là admin.html
        }
        return "redirect:/"; // Nếu không phải ADMIN, chuyển hướng về trang chính
    }
}