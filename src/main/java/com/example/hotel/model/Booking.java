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

    // Các getter và setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

    public String getRoomType() { return roomType; } // Cập nhật getter
    public void setRoomType(String roomType) { this.roomType = roomType; } // Cập nhật setter

    public int getNumberOfGuests() { return numberOfGuests; }
    public void setNumberOfGuests(int numberOfGuests) { this.numberOfGuests = numberOfGuests; }
}