package com.example.dubboproviderdemo.service.impl;


import com.example.dubboproviderdemo.service.providerService;
import com.pamirs.pradar.Pradar;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class providerServiceImpl implements providerService {

    @Value("${rabbit_topickey}")
    private String rabbitmqtopic;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public String toRabbitmq(String username) {

        System.out.println("To is>>>>>>>>" + Pradar.isClusterTest());

        System.out.println("To Rabbitmq>>>>>>>>" + username);
        Date date = new Date();
        String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        System.out.println("[demoQueue] send msg: " + dateString);
        // 第一个参数为刚刚定义的队列名称
        this.rabbitTemplate.convertAndSend(rabbitmqtopic, username);
        return "add User to kafka Successful!!";
    }

}
