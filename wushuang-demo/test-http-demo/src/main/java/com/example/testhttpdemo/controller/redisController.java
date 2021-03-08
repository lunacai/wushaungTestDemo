package com.example.testhttpdemo.controller;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;

@RestController
public class redisController {
    @Value(value = "${active_topic}")
    private String activeTopic;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    public JmsMessagingTemplate jmsMessagingTemplate;

    @GetMapping("/getRedis/{key}")
    public String sendToRedis(@PathVariable String key) {
        System.out.println("redis>>>>>>>>" + key);
        return redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/setRedis")
    public String setToRedis(String key) {
        System.out.println("redis>>>>>>>>" + key);
        String ids = String.valueOf(key.length());
        redisTemplate.opsForValue().set(ids,key);
        redisTemplate.opsForValue().set(key,key);
        System.out.println("key>>>>>>>>" + redisTemplate.opsForValue().get(key));
        return redisTemplate.opsForValue().get(ids);
    }

    @GetMapping("/activeMq")
    public String sendToActive(String msg){
        Destination destopic= new ActiveMQTopic(activeTopic);
        jmsMessagingTemplate.convertAndSend(destopic, msg);
        return "success";
    }

    @RequestMapping(value = "/allRedis")
    public String allRedis(@RequestBody String key) {
        redisTemplate.opsForValue().set(key,key);
        System.out.println("key>>>>>>>>" + redisTemplate.opsForValue().get(key));
        return redisTemplate.opsForValue().get(key);
    }
}
