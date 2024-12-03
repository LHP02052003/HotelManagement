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
    private boolean isCheckedIn;
    private boolean isCheckedOut;
    private String checkInQrCodeBase64;
    private String checkOutQrCodeBase64;

    // Getter và setter cho check-in QR
    public String getCheckInQrCodeBase64() {
        return checkInQrCodeBase64;
    }

    public void setCheckInQrCodeBase64(String checkInQrCodeBase64) {
        this.checkInQrCodeBase64 = checkInQrCodeBase64;
    }

    // Getter và setter cho check-out QR
    public String getCheckOutQrCodeBase64() {
        return checkOutQrCodeBase64;
    }

    public void setCheckOutQrCodeBase64(String checkOutQrCodeBase64) {
        this.checkOutQrCodeBase64 = checkOutQrCodeBase64;
    }
    public String getBookingStatus() {
        if (isCheckedOut) {
            return "Check-out thành công";
        } else if (isCheckedIn) {
            return "Check-in thành công";
        } else {
            return "Chưa check-in";
        }
    }




}