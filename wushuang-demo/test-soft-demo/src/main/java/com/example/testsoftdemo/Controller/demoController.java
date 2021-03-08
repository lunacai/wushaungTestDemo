package com.example.testsoftdemo.Controller;

import com.example.clientdemo.RedisMedol;
import com.example.dubboproviderdemo.service.UserService;
import com.example.testsoftdemo.kafka.KfProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/all")
public class demoController {
    @Autowired
    private UserService userService;

    @Autowired
    private KfProducer kafkaproducer;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Value("${kafka_topic}")
    private String kafkaTopic;

    @Value(value = "${kafka_ip}")
    private String KAFKA_IP;

    @GetMapping("/sendToAll")
    public String sendToDubbo(String username) {
        RedisMedol user = new RedisMedol();
        user.setKey(String.valueOf(username.length()));
        user.setContent("dubbo_" + username);
        System.out.println("Http Provider>>>>>>>" + userService.addUser(user));

        System.out.println("kafka>>>>>>>>" + username);
        kafkaproducer.sendSyn(KAFKA_IP, kafkaTopic, username);

        redisTemplate.opsForValue().set(username, username);
        redisTemplate.opsForValue().append(username, username + "append");
        return redisTemplate.opsForValue().get(username);
    }
}
