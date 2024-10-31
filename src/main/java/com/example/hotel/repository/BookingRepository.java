package com.example.hotel.repository;

import com.example.hotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUsername(String username);
    long countByRoomType(int roomType); // Đếm số lượng phòng đã đặt theo loại phòng
}
