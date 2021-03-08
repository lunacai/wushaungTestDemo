package com.example.testcanacedemo.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
public class kafkaListener {

    @KafkaListener(topics = "spring_kafka")
    public void listen(String data) {
        System.out.println("kafkaconfig =listen======="+data);
    }
}
