package com.example.hotel.repository;

import com.example.hotel.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByUsername(String username); // Tìm yêu cầu theo customerId
    List<ServiceRequest> findByStatus(String status); // Tìm yêu cầu theo trạng thái
}
