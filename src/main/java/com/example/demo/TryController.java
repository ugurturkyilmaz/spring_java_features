package com.example.demo;


import com.example.demo.jwt.JwtConfiguration;
import com.example.demo.jwt.JwtService;
import com.example.demo.kafka.KafkaProducerService;
import com.example.demo.logging.LoggingService;
import com.example.demo.logging.LoggingServiceLog4J;
import com.example.demo.redis.RedisPubService;
//import org.apache.kafka.common.protocol.types.Field;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TryController {

    private final TryServiceImpl testService;
    private final KafkaProducerService kafkaProducerService;
    private final RedisPubService redisPubService;
    private final JwtConfiguration jwtConfiguration;
    private final JwtService jwtService;
    private final LoggingService loggingService;
    private final LoggingServiceLog4J loggingServiceLog4J;

    public TryController(TryServiceImpl testService, KafkaProducerService kafkaProducerService, RedisPubService redisPubService, JwtConfiguration jwtConfiguration, JwtService jwtService, LoggingService loggingService, LoggingServiceLog4J loggingServiceLog4J) {
        this.testService = testService;
        this.kafkaProducerService = kafkaProducerService;
        this.redisPubService = redisPubService;
        this.jwtConfiguration = jwtConfiguration;
        this.jwtService = jwtService;
        this.loggingService = loggingService;
        this.loggingServiceLog4J = loggingServiceLog4J;
    }

    //circiutbreaker
    @GetMapping("/guest/test")
    public String testCircuitBreaker(@RequestParam(required = false) boolean isError) {
        return testService.testCircuitBreaker(isError);
    }

    /*
    //kafka
    @GetMapping("/guest/kafka")
    public void testKafka(@RequestParam() String message) {
        kafkaProducerService.sendMessage(message);
    }

     */

    //Redis pub-sub
    @GetMapping("/guest/redis")
    public void testRedis(@RequestParam() String message) {
        redisPubService.publish(message);
    }

    //Refresh token kullanılacaksa refresh token da dönmeli.
    @GetMapping("/guest/login")
    public String testLogin() {
        return jwtConfiguration.createToken(); //Token client'a döner.

    }

    @GetMapping("/user")
    public String userInfo() {
        return jwtService.getUserInfo();
    }

    @GetMapping("/guest/logging")
    public void logging(@RequestParam() String message) {
        //loggingService.log(message);
        loggingServiceLog4J.log(message);
    }

    //Refresh token alıp yeni bir token ile devam edebilmek için
    /*
    @GetMapping("/guest/refresh")
    public String refreshToken() {
        // 1. Refresh token valid mi?
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Geçersiz Refresh Token!");
        }

        // 2. Refresh token süresi dolmamış ve imzası doğruysa:
        String username = jwtUtil.getUsername(refreshToken);

        // 3. Yeni Access Token oluştur (isterseniz yeni Refresh de oluşturabilirsiniz)
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");
        String newAccessToken = jwtUtil.createAccessToken(username, claims);

        // 4. Döndür
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);

        // Eğer refresh token rotation yapmak istiyorsanız:
        // String newRefreshToken = jwtUtil.createRefreshToken(username);
        // response.put("refreshToken", newRefreshToken);

        return response;
    }

     */
}
