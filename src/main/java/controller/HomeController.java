package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homePage() {
        return "index";  // Tên của template hoặc trang HTML
    }

    @GetMapping("/index.html")
    public String redirectToRoot() {
        return "redirect:/";  // Chuyển hướng đến /
    }

    @PostMapping("/submit")
    public String handleSubmit() {
        // Xử lý logic và điều hướng tới trang khác nếu cần
        return "success";  // Trả về tên của template khác
    }
}

