package com.example.dubboxproviderdemo.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.example.clientdemo.RedisMedol;
import com.example.dubboxproviderdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class UserServiceProvider implements UserService {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public RedisMedol setUser(RedisMedol user) {
        System.out.println("dubbox>>>>>>"+user.getKey());
        String msg=user.getContent();
        kafkaTemplate.send("spring_kafka", msg);
        return user;
    }
}