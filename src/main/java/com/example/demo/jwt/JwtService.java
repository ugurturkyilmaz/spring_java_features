package com.example.demo.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public String getUserInfo() {
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) currentAuth.getDetails();
        return userInfo.getFirstName() + " " + userInfo.getLastName() + " " +
                userInfo.getMsisdn() + " " + userInfo.getRedisId() + " " + userInfo.getTckn();
    }
}
