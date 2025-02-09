package com.example.demo;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TestServiceImpl {

    @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallbackMethod")
    public String testCircuitBreaker(boolean isError) {
        if(isError) {
            throw new RuntimeException("error");
        }
        int length = 10000;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    public String fallbackMethod(CallNotPermittedException ex) {
        return ex.getMessage();
    }
}
