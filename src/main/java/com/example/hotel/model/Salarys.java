package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "salary_db")
@Data
public class Salarys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double baseSalary; // Lương cơ bản

    // Getters and Setters
}
