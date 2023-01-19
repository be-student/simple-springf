package com.example.testing.controller.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final int SECOND = 1000;
    @Value("${jwt.secret}")
    private String jwtSecret;

    @GetMapping("/free")
    public ResponseEntity<AuthResponseDto> free() {
        String jwt = generateJwtToken(1);
        return new ResponseEntity<>(new AuthResponseDto(jwt, 1), null, 200);
    }

    private String generateJwtToken(int userId) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setIssuer("test")
                .claim("userId", userId)
                .claim("authorities", "ROLE_USER")
                .signWith(key)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 60 * 60 * SECOND))
                .compact();
    }
}
