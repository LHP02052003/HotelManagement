package com.example.hotel.repository;

import com.example.hotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUsername(String username);
    long countByRoomType(String roomType);  // Cập nhật với kiểu String

    // Tìm các đơn đặt phòng theo khoảng thời gian
    List<Booking> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);

    // Phương thức tính tổng doanh thu trong một khoảng thời gian (custom query)
    @Query("SELECT COALESCE(SUM(b.price), 0) FROM Booking b WHERE b.checkInDate BETWEEN :startDate AND :endDate")
    Double calculateTotalRevenue(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);



}