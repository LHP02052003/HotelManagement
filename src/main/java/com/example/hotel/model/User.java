package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "hotel_db")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    private String role; // "ADMIN" hoặc "USER"
    private String fullName;
    private String dateOfBirth;
    private String phoneNumber;
    private String email;
    private String address;
}

