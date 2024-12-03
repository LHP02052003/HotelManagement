package com.example.hotel.controller;

import com.example.hotel.model.RequestSupport;
import com.example.hotel.service.RequestSupportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class UserRequestController {

    @Autowired
    private RequestSupportService requestSupportService;

    // Hiển thị form yêu cầu hỗ trợ
    @GetMapping("/request-support")
    public String showRequestSupportForm(Model model) {
        model.addAttribute("requestSupport", new RequestSupport());
        return "request-support";  // Trả về trang request-support.html
    }

    // Xử lý khi người dùng gửi yêu cầu hỗ trợ
    @PostMapping("/request-support")
    public String submitSupportRequest(@ModelAttribute RequestSupport requestSupport, HttpSession session) {
        String username = (String) session.getAttribute("username");  // Lấy username từ session
        requestSupport.setUsername(username);
        requestSupport.setStatus("Đang chờ");  // Gán trạng thái ban đầu là Đang chờ
        requestSupportService.saveRequest(requestSupport);  // Lưu yêu cầu hỗ trợ
        return "redirect:/customer/request-support";  // Chuyển hướng về trang yêu cầu hỗ trợ
    }

    // Hiển thị danh sách yêu cầu hỗ trợ của người dùng
    @GetMapping("/view-support-requests")
    public String viewUserRequests(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");  // Lấy username từ session
        List<RequestSupport> userRequests = requestSupportService.getRequestsByUsername(username);  // Lấy yêu cầu theo username
        model.addAttribute("userRequests", userRequests);  // Gửi yêu cầu hỗ trợ đến view
        return "view-support-requests";  // Trả về trang để hiển thị yêu cầu hỗ trợ
    }
}
