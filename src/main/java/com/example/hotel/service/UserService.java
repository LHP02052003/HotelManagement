package com.example.hotel.service;

import javax.annotation.PostConstruct;
import com.example.hotel.model.User;
import com.example.hotel.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setRole("CUSTOMER");
        return userRepository.save(user);
    }

    @PostConstruct
    public void init() {
        // Tạo tài khoản admin nếu chưa có
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Mật khẩu: admin123
            admin.setRole("ADMIN"); // Đặt vai trò là ADMIN
            userRepository.save(admin);
        }
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);  // Tìm người dùng theo tên
    }
}
