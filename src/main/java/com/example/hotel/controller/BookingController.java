    package com.example.hotel.controller;

    import com.example.hotel.model.Booking;
    import com.example.hotel.service.BookingService;
    import com.google.zxing.BarcodeFormat;
    import com.google.zxing.WriterException;
    import com.google.zxing.client.j2se.MatrixToImageWriter;
    import com.google.zxing.common.BitMatrix;
    import com.google.zxing.qrcode.QRCodeWriter;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.io.ByteArrayOutputStream;
    import java.io.IOException;
    import java.time.LocalDate;
    import java.util.Base64;
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
                                    @RequestParam String roomType, // Loại phòng
                                    @RequestParam int guests,
                                    HttpSession session,
                                    Model model) {
            String username = (String) session.getAttribute("username");
            String role = (String) session.getAttribute("role");
            if (username == null) {
                return "redirect:/login"; // Chuyển về trang đăng nhập nếu chưa đăng nhập
            }
            if ("ADMIN".equals(role)) {
                model.addAttribute("errorMessage", "Admin không thể đặt phòng.");
                return "booking-error"; // Trang lỗi đặt phòng
            }
            // Kiểm tra phòng còn trống
            int totalRooms = switch (roomType) {
                case "DELUXE" -> 60;
                case "COUPLE" -> 50;
                case "FAMILY" -> 40;
                default -> 0;
            };

            long bookedRooms = bookingService.countRoomsByType(roomType);
            if (totalRooms - bookedRooms <= 0) {
                model.addAttribute("soldOutMessage", "Loại phòng này đã hết. Vui lòng chọn loại phòng khác.");
                return "booking-failed";
            }

            // Lấy số phòng tiếp theo
            String roomNumber;
            try {
                roomNumber = bookingService.getNextRoomNumber(roomType);
            } catch (IllegalStateException e) {
                model.addAttribute("soldOutMessage", e.getMessage());
                return "booking-failed";
            }
            double roomPricePerNight = 0;
            switch (roomType) {
                case "DELUXE":
                    roomPricePerNight = 7; // Giá phòng DELUXE ( đơn vị triệu vnđ )
                    break;
                case "COUPLE":
                    roomPricePerNight = 9.0; // Giá phòng COUPLE  ( đơn vị triệu vnđ )
                    break;
                case "FAMILY":
                    roomPricePerNight = 11.5; // Giá phòng FAMILY  ( đơn vị triệu vnđ )
                    break;
            }

            // Tính số ngày ở
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.parse(checkInDate), LocalDate.parse(checkOutDate));

            // Tính tổng giá phòng
            double totalPrice = roomPricePerNight * daysBetween; // Giá phòng x Số ngày
            // Tạo booking mới
            Booking booking = new Booking();
            booking.setUsername(username);
            booking.setCheckInDate(LocalDate.parse(checkInDate));
            booking.setCheckOutDate(LocalDate.parse(checkOutDate));
            booking.setRoomType(roomType);
            booking.setNumberOfGuests(guests);
            booking.setRoomNumber(roomNumber);
            booking.setPrice(totalPrice);

            bookingService.saveBooking(booking);
            model.addAttribute("successMessage", "Đặt phòng thành công! Số phòng của bạn là " + roomNumber);
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

            // Tạo mã QR cho check-in và check-out và thêm vào model
            for (Booking booking : userBookings) {
                try {
                    // Tạo mã QR cho check-in
                    String checkInQrContent = "http://localhost:8080/booking/check-in/" + booking.getId();
                    QRCodeWriter qrCodeWriter = new QRCodeWriter();
                    BitMatrix checkInBitMatrix = qrCodeWriter.encode(checkInQrContent, BarcodeFormat.QR_CODE, 300, 300);
                    ByteArrayOutputStream checkInPngOutputStream = new ByteArrayOutputStream();
                    MatrixToImageWriter.writeToStream(checkInBitMatrix, "PNG", checkInPngOutputStream);
                    String checkInQrBase64 = Base64.getEncoder().encodeToString(checkInPngOutputStream.toByteArray());

                    // Tạo mã QR cho check-out
                    String checkOutQrContent = "http://localhost:8080/booking/check-out/" + booking.getId();
                    BitMatrix checkOutBitMatrix = qrCodeWriter.encode(checkOutQrContent, BarcodeFormat.QR_CODE, 300, 300);
                    ByteArrayOutputStream checkOutPngOutputStream = new ByteArrayOutputStream();
                    MatrixToImageWriter.writeToStream(checkOutBitMatrix, "PNG", checkOutPngOutputStream);
                    String checkOutQrBase64 = Base64.getEncoder().encodeToString(checkOutPngOutputStream.toByteArray());

                    // Thêm vào booking
                    booking.setCheckInQrCodeBase64(checkInQrBase64);
                    booking.setCheckOutQrCodeBase64(checkOutQrBase64);

                } catch (WriterException | IOException e) {
                    e.printStackTrace();
                }
            }

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
        @GetMapping("/check-in/{id}")
        public String checkIn(@PathVariable Long id, Model model) {
            Booking booking = bookingService.getBookingById(id);
            if (booking == null) {
                model.addAttribute("message", "Không tìm thấy booking.");
                return "checkin-result"; // Trang hiển thị kết quả
            }
            if (booking.isCheckedIn()) {
                model.addAttribute("message", "Booking đã check-in trước đó.");
                return "checkin-result";
            }

            booking.setCheckedIn(true);
            bookingService.saveBooking(booking);

            model.addAttribute("message", "Check-in thành công!");
            model.addAttribute("redirectUrl", "/booking/user-bookings");
            return "checkin-result"; // Trang hiển thị kết quả
        }


        @GetMapping("/check-out/{id}")
        public String checkOut(@PathVariable Long id, Model model) {
            Booking booking = bookingService.getBookingById(id);
            if (booking == null) {
                model.addAttribute("message", "Không tìm thấy booking.");
                return "checkin-result";
            }
            if (!booking.isCheckedIn()) {
                model.addAttribute("message", "Bạn chưa check-in. Không thể check-out.");
                return "checkin-result";
            }
            if (booking.isCheckedOut()) {
                model.addAttribute("message", "Booking đã check-out trước đó.");
                return "checkin-result";
            }

            booking.setCheckedOut(true);
            bookingService.saveBooking(booking);

            model.addAttribute("message", "Check-out thành công!");
            model.addAttribute("redirectUrl", "/booking/user-bookings");
            return "checkin-result";
        }


    }
