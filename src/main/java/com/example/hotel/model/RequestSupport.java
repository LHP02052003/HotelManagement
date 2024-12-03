    package com.example.hotel.model;

    import jakarta.persistence.*;
    import lombok.Data;

    @Entity
    @Table(name = "support")
    @Data
    public class RequestSupport {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String username;
        private String message;
        private String adminResponse;
        private String status;
        // Getters và setters
    }
