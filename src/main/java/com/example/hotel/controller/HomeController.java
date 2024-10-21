package com.example.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homePage(HttpSession session) {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/admin")
    public String adminPage(HttpSession session) {
        if (session.getAttribute("role") != null &&
                session.getAttribute("role").equals("ADMIN")) {
            return "admin";
        }
        return "redirect:/";
    }
}
