package com.example.hotel.service;

import com.example.hotel.model.ServiceRequest;
import com.example.hotel.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    // Lưu yêu cầu dịch vụ mới
    public void saveServiceRequest(ServiceRequest serviceRequest) {
        serviceRequestRepository.save(serviceRequest);
    }

    // Lấy yêu cầu dịch vụ theo customerId
    public List<ServiceRequest> findByUsername(String username) {
        return serviceRequestRepository.findByUsername(username);
    }

    // Lấy yêu cầu dịch vụ theo trạng thái
    public List<ServiceRequest> findByStatus(String status) {
        return serviceRequestRepository.findByStatus(status);
    }

    // Lấy tất cả yêu cầu dịch vụ
    public List<ServiceRequest> findAllRequests() {
        return serviceRequestRepository.findAll();
    }

    // Cập nhật trạng thái yêu cầu dịch vụ
    public ServiceRequest updateStatus(Long id, String status) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id).orElseThrow();
        serviceRequest.setStatus(status);
        return serviceRequestRepository.save(serviceRequest);
    }

    // Xóa yêu cầu dịch vụ
    public void deleteServiceRequest(Long id) {
        serviceRequestRepository.deleteById(id);
    }
}
