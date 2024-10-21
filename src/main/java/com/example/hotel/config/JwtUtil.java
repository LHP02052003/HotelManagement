package com.example.hotel.config;

import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET = "your-secret-key";

    // Tạo JWT token với username và thời gian hết hạn 1 ngày
    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_00L)) // 1 ngày
                .sign(Algorithm.HMAC256(SECRET));
    }

    // Xác thực và trích xuất thông tin từ token
    public String validateToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token)
                .getSubject();
    }
}
