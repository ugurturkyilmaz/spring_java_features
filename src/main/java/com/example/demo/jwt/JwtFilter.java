package com.example.demo.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtConfiguration jwtUtil;

    public JwtFilter(JwtConfiguration jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            // Token geçerliyse decode edip SecurityContext'e bilgileri yazalım:
            if (jwtUtil.validateToken(token)) {
                Claims claims = jwtUtil.getAllClaims(token);

                // Örnek: username'i subject'ten alalım rdsId
                String username = claims.getSubject();


                // Eğer roller varsa, GrantedAuthority listesi oluştururuz
                // Şimdilik basit bir Role_USER ile örnek
                // Collections.singletonList(...) -> Tek bir rol ekler
                // Ya da roles’leri claims’ten çekip dönüştürebilirsiniz.
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                username,            // principal (kimlik bilgisi)
                                null,                // credentials (şifre vs.)
                                Collections.emptyList() // authorities (roller)
                        );

                UserInfo userInfo = new UserInfo(username, claims.get("firstName").toString(), claims.get("msisdn").toString(), claims.get("lastName").toString(), claims.get("tckn").toString());
                authToken.setDetails(userInfo);

                // İşte burada SecurityContext'e yazıyoruz:
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }

        // Filtre zincirine devam
        chain.doFilter(request, response);
    }
}
