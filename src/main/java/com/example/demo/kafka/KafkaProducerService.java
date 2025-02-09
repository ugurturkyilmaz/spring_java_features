package com.example.demo.kafka;

//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    /*private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        String topicName = "example-topic";
        kafkaTemplate.send(topicName, message);
        System.out.println("Gönderilen mesaj: " + message);
    }

     */
}