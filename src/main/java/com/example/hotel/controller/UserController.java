package com.example.hotel.controller;

import com.example.hotel.model.User;
import com.example.hotel.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
            return "user-profile";
        }
        return "redirect:/login";
    }


    @PostMapping("/profile")
    public String updateProfile( @ModelAttribute("user") User updatedUser,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            boolean isUpdated = userService.updateUser(updatedUser, username) != null;
            if (isUpdated) {
                redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
            }
        }
        return "redirect:/profile";
    }

}
