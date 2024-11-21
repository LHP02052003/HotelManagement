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
                                @RequestParam String roomType,  // Thay đổi thành String
                                @RequestParam int guests,
                                HttpSession session,
                                Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển về trang đăng nhập
        }

        // Lấy tổng số phòng dựa vào roomType
        int totalRooms = switch (roomType) {
            case "DELUXE" -> 60;
            case "COUPLE" -> 50;
            case "FAMILY" -> 40;
            default -> 0;
        };

        // Đếm số phòng đã được đặt
        long bookedRooms = bookingService.countRoomsByType(roomType);

        // Kiểm tra nếu hết phòng
        if (totalRooms - (int) bookedRooms <= 0) {
            // Nếu không còn phòng, hiển thị thông báo
            model.addAttribute("soldOutMessage", "Loại phòng này đã hết. Vui lòng chọn loại phòng khác.");
            return "booking-failed"; // Tên trang hiển thị thông báo lỗi, ví dụ: booking-failed.html
        }

        // Tiếp tục xử lý đặt phòng
        Booking booking = new Booking();
        booking.setUsername(username);
        booking.setCheckInDate(LocalDate.parse(checkInDate));
        booking.setCheckOutDate(LocalDate.parse(checkOutDate));
        booking.setRoomType(roomType);  // Lưu trữ tên loại phòng
        booking.setNumberOfGuests(guests);

        bookingService.saveBooking(booking);
        model.addAttribute("successMessage", "Đặt phòng thành công!");
        return "booking-success";
    }



    @GetMapping("/booking-page")
    public String bookingPage(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        String username = (String) session.getAttribute("username");

        if ("ADMIN".equals(role)) {
            return "redirect:/admin";
        } else {
            // Nếu là user, lấy danh sách booking của user
            List<Booking> bookings = bookingService.findByUsername(username);
            model.addAttribute("bookings", bookings);
            return "user-bookings";
        }
    }


    @GetMapping("/user-bookings")
    public String userBookings(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển về trang đăng nhập
        }

        // Lấy danh sách đặt phòng của người dùng hiện tại
        List<Booking> userBookings = bookingService.findByUsername(username);
        model.addAttribute("bookings", userBookings);

        return "user-bookings"; // Tên trang HTML cho người dùng
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, HttpSession session) {
        bookingService.deleteBooking(id);

        // Kiểm tra vai trò người dùng
        String role = (String) session.getAttribute("role");
        if ("ADMIN".equals(role)) {
            // Nếu là admin, quay lại trang admin
            return "redirect:/admin";
        } else {
            // Nếu là user, quay lại trang đặt phòng của user
            return "redirect:/booking/user-bookings";
        }
    }

}
