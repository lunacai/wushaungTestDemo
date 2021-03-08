package com.example.testsoftdemo.Controller;

import com.example.testsoftdemo.kafka.KfProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class kafkaController {

    @Autowired
    private KfProducer kafkaproducer;

    @Value("${kafka_topic}")
    private String kafkaTopic;

    @Value(value = "${kafka_ip}")
    private String KAFKA_IP;

    @GetMapping("/sendToKafka")
    public String sendToKafka(String username) {
        System.out.println("kafka>>>>>>>>" + username);
        kafkaproducer.sendSyn(KAFKA_IP, kafkaTopic, username.toString());
        return "Send to Kafka successfully";
    }
}
