package com.example.userservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.util.Date;
public class JwtUtil {

    private static final String SECRET_KEY = "MY_SECRET_KEY_123";
    private static final long EXPIRATON_TIME = 86400000;

    // generate token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)   // konu: tokenın ait olduğu kullanıcı
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATON_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Tokendan username (subject) bilgisini çekmek
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // token valid mi kontrol etmek vb.
    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaims(token);
            // expiration ve subject gibi kontroller burada yapılabilir
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }





}
