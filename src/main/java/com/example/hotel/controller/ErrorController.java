package com.example.hotel.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Thêm logic xử lý lỗi nếu cần thiết
        return "error";  // Trả về một trang lỗi tùy chỉnh
    }


}
