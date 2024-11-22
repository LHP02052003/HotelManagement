package com.example.hotel.controller;

import com.example.hotel.model.ServiceRequest;
import com.example.hotel.service.ServiceRequestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/service-request")
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    // Xem tất cả yêu cầu dịch vụ (dành cho admin)
    @GetMapping("/all")
    public String viewAllServiceRequests(Model model) {
        List<ServiceRequest> serviceRequests = serviceRequestService.findAllRequests();
        model.addAttribute("serviceRequests", serviceRequests); // Đảm bảo rằng danh sách dịch vụ được đưa vào model
        return "admin-services"; // Tên view sẽ là admin-services.html
    }

    // Xem yêu cầu dịch vụ của khách hàng (dành cho khách hàng)
    @GetMapping("/customer/{username}")
    public String viewCustomerServiceRequests(@PathVariable String username, Model model) {
        List<ServiceRequest> serviceRequests = serviceRequestService.findByUsername(username);
        model.addAttribute("serviceRequests", serviceRequests);
        return "service-details";
    }

    // Cập nhật trạng thái yêu cầu dịch vụ
    @PostMapping("/update-status/{id}")
    public String updateServiceRequestStatus(@PathVariable Long id, @RequestParam String status) {
        serviceRequestService.updateStatus(id, status);
        return "redirect:/service-request/all"; // Quay lại trang danh sách yêu cầu dịch vụ
    }

    // Xóa yêu cầu dịch vụ
    @PostMapping("/delete/{id}")
    public String deleteServiceRequest(@PathVariable Long id) {
        serviceRequestService.deleteServiceRequest(id);
        return "redirect:/service-request/all"; // Quay lại trang danh sách yêu cầu dịch vụ
    }

    // Thêm yêu cầu dịch vụ mới
    // Cập nhật phương thức tạo yêu cầu dịch vụ để sử dụng thông tin số phòng và kiểu phòng
    @PostMapping("/create")
    public String createServiceRequest(@RequestParam List<String> serviceName,
                                       @RequestParam String roomType,
                                       @RequestParam String roomNumber, // Thêm số phòng
                                       HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển về trang đăng nhập
        }
        // Lưu tất cả các dịch vụ đã chọn
        for (String service : serviceName) {
            ServiceRequest serviceRequest = new ServiceRequest();
            serviceRequest.setUsername(username);
            serviceRequest.setRoomType(roomType);  // Lưu trữ tên loại phòng
            serviceRequest.setRoomNumber(roomNumber); // Lưu số phòng
            serviceRequest.setServiceName(service);
            serviceRequest.setStatus("Pending");

            // Lưu yêu cầu dịch vụ
            serviceRequestService.saveServiceRequest(serviceRequest);
        }

        // Chuyển hướng người dùng tới trang yêu cầu dịch vụ của mình
        return "redirect:/service-request/customer/" + username;
    }

}