package com.example.demo.redis;

import org.springframework.stereotype.Service;

@Service
public class RedisSubService {

    public void handleMessage(String message) {
       //System.out.println(STR."Subscriber - Mesaj alındı: \{ message }" );
        System.out.println(message + " mesajı ulaştı");

    }
}
