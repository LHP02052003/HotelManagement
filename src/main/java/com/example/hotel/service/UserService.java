
package com.example.hotel.service;

import com.example.hotel.model.User;
import com.example.hotel.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public User registerUser(User user) {
        // Đặt vai trò mặc định là CUSTOMER
        user.setRole("CUSTOMER");
        return userRepository.save(user); // Lưu người dùng mới vào DB
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
    public boolean isUsernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }



    public User updateUser(User updatedUser, String username) {
        User existingUser = userRepository.findByUsername(username).orElse(null);
        if (existingUser != null) {
            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setAddress(updatedUser.getAddress());
            return userRepository.save(existingUser); // Lưu thay đổi vào cơ sở dữ liệu
        }
        return null;
    }
}
