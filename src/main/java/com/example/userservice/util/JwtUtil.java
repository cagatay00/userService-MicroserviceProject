package com.example.userservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.example.userservice.service.TokenBlacklistService;

@Component
public class JwtUtil {

    // Must be stored in safe (örn. environment variable)
    private static final String SECRET_KEY = "MY_SECRET_KEY_123";
    private static final long EXPIRATION_TIME = 86400000; // 1 gün (ms)

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    // Token oluşturma metodu
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    // Token’dan kullanıcı adını çıkarma
    public String extractUsername(String token) {
        try {
            Claims claims = getClaims(token);
            System.out.println("Extracted username: " + claims.getSubject()); // Debugging
            return claims.getSubject();
        } catch (Exception e) {
            System.out.println("Token extraction failed: " + e.getMessage()); // Debugging
            return null;
        }
    }

    // Token’ın geçerliliğini kontrol etme
    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // Token’dan claim’leri çıkarma
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims extractClaims(String token) {
        if (tokenBlacklistService.isTokenBlacklisted(token)) {
            throw new IllegalArgumentException("Token is blacklisted.");
        }
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
