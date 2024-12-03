package com.example.hotel.controller;

import com.example.hotel.model.User;
import com.example.hotel.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
        boolean exists = userService.isUsernameExists(username);
        return ResponseEntity.ok(exists);
    }

    // Đăng ký tài khoản mới
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String fullName,
                           @RequestParam String dateOfBirth,
                           @RequestParam String phoneNumber,
                           @RequestParam(required = false) String email,
                           @RequestParam(required = false) String address,
                           RedirectAttributes redirectAttributes) {
        try {
            if (userService.isUsernameExists(username)) {
                redirectAttributes.addFlashAttribute("error", "Tên người dùng đã tồn tại!");
                return "redirect:/register";
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password)); // Mã hóa mật khẩu
            user.setFullName(fullName);
            user.setDateOfBirth(dateOfBirth);
            user.setPhoneNumber(phoneNumber);
            user.setEmail(email);
            user.setAddress(address);

            userService.registerUser(user);  // Đăng ký người dùng
            redirectAttributes.addFlashAttribute("message", "Đăng ký thành công!");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đăng ký thất bại!");
            return "redirect:/register";
        }
    }


    // Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username,
                                        @RequestParam String password,
                                        HttpSession session) {
        User existingUser = userService.findByUsername(username);
        if (existingUser != null && passwordEncoder.matches(password, existingUser.getPassword())) {
            // Lưu thông tin người dùng vào session
            session.setAttribute("username", existingUser.getUsername());
            session.setAttribute("role", existingUser.getRole());
            // Điều hướng dựa trên vai trò người dùng
            String redirectUrl = existingUser.getRole().equals("ADMIN") ? "/" : "/";
            return ResponseEntity.ok(redirectUrl);  // Trả về URL để chuyển hướng
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai tài khoản hoặc mật khẩu!");
    }

    // Đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Hủy session
        return "redirect:/";
    }
}
