package com.example.hotel.service;

import com.example.hotel.config.ResourceNotFoundException;
import com.example.hotel.model.RequestSupport;
import com.example.hotel.repository.RequestSupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestSupportService {

    @Autowired
    private RequestSupportRepository requestSupportRepository;

    public void saveRequest(RequestSupport requestSupport) {
        requestSupportRepository.save(requestSupport);  // Lưu yêu cầu hỗ trợ
    }

    public List<RequestSupport> getAllRequests() {
        return requestSupportRepository.findAll();  // Lấy tất cả yêu cầu hỗ trợ
    }

    public RequestSupport getRequestById(Long id) {
        return requestSupportRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Request not found"));  // Lấy yêu cầu theo id
    }

    public List<RequestSupport> getRequestsByUsername(String username) {
        return requestSupportRepository.findByUsername(username);  // Lấy yêu cầu theo username
    }

    public void deleteRequestById(Long id) {
        requestSupportRepository.deleteById(id);  // Hibernate sẽ xóa trực tiếp mà không cần select
    }
}
