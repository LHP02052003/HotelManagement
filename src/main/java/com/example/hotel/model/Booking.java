package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "room_db")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;  // Tên người dùng đặt phòng
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String roomType; // Thay đổi tên từ rooms thành roomType
    private int numberOfGuests;  // Số lượng khách
    private String roomNumber; // Số phòng, ví dụ: "500A"
    private double price;



}