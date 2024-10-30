package com.example.hotel.controller;

import com.example.hotel.model.Booking;
import com.example.hotel.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public String createBooking(@RequestParam String checkInDate,
                                @RequestParam String checkOutDate,
                                @RequestParam int roomType, // Thay đổi từ rooms sang roomType
                                @RequestParam int guests,
                                HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển về trang đăng nhập
        }

        Booking booking = new Booking();
        booking.setUsername(username);
        booking.setCheckInDate(LocalDate.parse(checkInDate));
        booking.setCheckOutDate(LocalDate.parse(checkOutDate));
        booking.setRoomType(roomType); // Cập nhật gọi setter này
        booking.setNumberOfGuests(guests);  // Lưu số người đăng ký

        bookingService.saveBooking(booking);
        return "redirect:/";
    }


    @GetMapping("/user-bookings")
    public String userBookings(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        List<Booking> bookings = bookingService.findByUsername(username);
        model.addAttribute("bookings", bookings);
        return "user-bookings";  // Tên view cần khớp với tên file
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/booking/user-bookings";
    }
}
