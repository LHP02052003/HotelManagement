package com.example.hotel.controller;

import com.example.hotel.model.ServiceRequest;
import com.example.hotel.service.ServiceRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {
    private final ServiceRequestService serviceRequestService;

    public PageController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }
    // Đường dẫn cho admin services
    @GetMapping("/admin/services")
    public String viewAllServiceRequests(Model model) {
        List<ServiceRequest> serviceRequests = serviceRequestService.findAllRequests();
        model.addAttribute("serviceRequests", serviceRequests); // Đảm bảo rằng danh sách dịch vụ được đưa vào model
        return "admin-services"; // Tên view sẽ là admin-services.html
    }

    // Đường dẫn cho customer services
    @GetMapping("/customer/services")
    public String customerServices() {
        return "customer-services"; // trả về file customer-services.html
    }
    // đường đẫn cho danh sách khách hàng
    @GetMapping("/customer/service-details")
    public String customerServiceDetails(Model model) {
        List<ServiceRequest> serviceRequests = serviceRequestService.findAllRequests();
        model.addAttribute("serviceRequests", serviceRequests);
        return "service-details"; // trả về file customer-services.html
    }
    @GetMapping("/map")
    public String pasmap() {
        return "ggmap";
    }
}

