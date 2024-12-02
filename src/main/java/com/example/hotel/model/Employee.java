package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Employee")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // Tên nhân viên
    private String position; // Chức vụ
    private String department; // Phòng ban
    private double salary; // Lương
    private int workingHours; // Giờ làm việc
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dates> attendanceRecords;

    // Getters and Setters
}
