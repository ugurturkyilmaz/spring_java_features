package com.example.demo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class JwtConfiguration {
    private static final String SECRET_KEY = "BuCokDahaUzunVeEnAz32KarakterliGizliBirAnahtar123!";
    private static final long REFRESH_TOKEN_VALIDITY = 1000L * 60 * 60 * 24 * 7;
    public String createToken() {

        long now = System.currentTimeMillis();
        long expirationTime = 300000; // 5 dk
        Random random = new Random();
        int number = random.nextInt(20);
        // Custom claims'leri bir Map içinde tutabiliriz:
        Map<String, Object> claims = new HashMap<>();
        claims.put("msisdn", number);
        claims.put("firstName", "Uğur" + number);
        claims.put("lastName", "Türkyılmaz" + number);
        claims.put("tckn", "testt" + number);
        //Bilgileri servisten çekiyoruz gibi düşün.

        return Jwts.builder()
                .setClaims(claims)             // Eklediğimiz custom alanlar
                .setSubject(String.valueOf(number))          // Genelde subject’i kullanıcı adı (veya ID) tutmak iyi olur
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Refresh Token oluştur
    public String createRefreshToken(String username) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + REFRESH_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);  // parseClaimsJws hata fırlatmazsa token geçerli
            return true;
        } catch (JwtException e) {
            // Hatalı veya süresi geçmiş token vs.
            return false;
        }
    }

    public String extractUsername(String token) {
        return getAllClaims(token).getSubject(); // subject alanına yazdığımız değeri döndürür
    }

    public Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
