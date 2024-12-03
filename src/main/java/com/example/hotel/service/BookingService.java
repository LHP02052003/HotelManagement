package com.example.hotel.service;

import com.example.hotel.model.Booking;
import com.example.hotel.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public List<Booking> findByUsername(String username) {
        return bookingRepository.findByUsername(username);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }


    // Thêm phương thức này
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll(); // Giả định rằng bookingRepository đã có phương thức này
    }

    public long countRoomsByType(String roomType) {
        return bookingRepository.countByRoomType(roomType); // Cập nhật với kiểu String
    }
    public String getNextRoomNumber(String roomType) {
        int startFloor = 5; // Tầng bắt đầu
        int roomsPerFloor = 10; // Số phòng mỗi tầng
        int maxFloor = 15; // Tầng tối đa

        // Ký tự đại diện loại phòng
        String roomSuffix = switch (roomType) {
            case "DELUXE" -> "S";
            case "COUPLE" -> "D";
            case "FAMILY" -> "F";
            default -> throw new IllegalArgumentException("Loại phòng không hợp lệ: " + roomType);
        };

        // Lấy danh sách các phòng đã được đặt
        List<Booking> bookings = bookingRepository.findAll();

        // Tập hợp các phòng đã được sử dụng
        Set<String> usedRooms = new HashSet<>();
        Map<Integer, Integer> floorRoomCount = new HashMap<>();
        for (Booking booking : bookings) {
            String roomNumber = booking.getRoomNumber();
            usedRooms.add(roomNumber);

            // Lấy số tầng từ số phòng
            int floor = Integer.parseInt(roomNumber.substring(0, roomNumber.length() - 3));
            floorRoomCount.put(floor, floorRoomCount.getOrDefault(floor, 0) + 1);
        }

        // Duyệt qua các tầng từ tầng 5 đến tầng 15
        for (int floor = startFloor; floor <= maxFloor; floor++) {
            int currentFloorCount = floorRoomCount.getOrDefault(floor, 0);

            // Nếu tầng hiện tại chưa đủ 10 phòng, tiếp tục kiểm tra
            if (currentFloorCount < roomsPerFloor) {
                // Tìm phòng theo thứ tự từ 00 đến 09
                for (int room = 0; room < roomsPerFloor; room++) {
                    String roomNumber = String.format("%d%02d%s", floor, room, roomSuffix);
                    if (!usedRooms.contains(roomNumber)) {
                        return roomNumber; // Trả về số phòng đầu tiên trống
                    }
                }
            }
        }

        // Nếu không còn phòng trống
        throw new IllegalStateException("Không còn phòng trống cho loại: " + roomType);
    }


    //tính tổng boking theo khoang thoi gian
    public double calculateTotalRevenue(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.findByCheckInDateBetween(startDate, endDate);

        // Tính tổng giá từ các booking
        return bookings.stream()
                .mapToDouble(Booking::getPrice) // Lấy giá từ từng Booking
                .sum();
    }

}
