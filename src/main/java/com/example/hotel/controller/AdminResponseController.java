package com.example.hotel.controller;

import com.example.hotel.model.RequestSupport;
import com.example.hotel.service.RequestSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminResponseController {
    @Autowired
    private RequestSupportService requestSupportService;

    @GetMapping("/respond-support")
    public String showRespondSupportForm(Model model) {
        List<RequestSupport> requests = requestSupportService.getAllRequests();
        model.addAttribute("requests", requests);
        return "respond-support";  // Trả về trang respond-support.html thay vì redirect
    }

    @PostMapping("/respond-support")
    public String respondToRequest(@RequestParam Long requestId, @RequestParam String response) {
        RequestSupport request = requestSupportService.getRequestById(requestId);
        request.setAdminResponse(response);
        request.setStatus("Đã tiếp nhận");  // Cập nhật trạng thái khi admin phản hồi
        requestSupportService.saveRequest(request);
        return "redirect:/admin/respond-support"; // Quay lại trang admin
    }

    @PostMapping("/delete-support")
    public String deleteRequest(@RequestParam Long requestId) {
        requestSupportService.deleteRequestById(requestId);
        return "redirect:/admin/respond-support"; // Quay lại trang dịch vụ của admin sau khi xóa phiếu
    }
}
