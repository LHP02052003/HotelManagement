package com.example.hotel.repository;

import com.example.hotel.model.RequestSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestSupportRepository extends JpaRepository<RequestSupport, Long> {
    // Truy vấn theo username
    List<RequestSupport> findByUsername(String username);  // Thêm phương thức tìm theo username
}
