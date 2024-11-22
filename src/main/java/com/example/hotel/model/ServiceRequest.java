package com.example.hotel.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "service_request")
@Data
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // Khách hàng yêu cầu dịch vụ
    private String roomNumber; // Số phòng, ví dụ: "500A"
    //private Long roomId; // Phòng của khách hàng
    private String roomType; // Thay đổi tên từ rooms thành roomType
    private String serviceName; // Tên dịch vụ (e.g., "Gọi đồ ăn", "Gọi taxi")
    private String status; // Trạng thái yêu cầu dịch vụ (e.g., "Pending", "Completed", "Cancelled")

    // Getter và Setter sẽ được Lombok tạo tự động nhờ @Data
}