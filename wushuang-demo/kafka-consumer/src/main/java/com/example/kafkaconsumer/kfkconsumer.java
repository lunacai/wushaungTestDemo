package com.example.kafkaconsumer;

import com.example.clientdemo.userAllModel;
import com.example.kafkaconsumer.mapper.UserMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class kfkconsumer {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;
    String endpoint = "oss-cn-beijing.aliyuncs.com";
    String accessKeyId = "";
    String accessKeySecret = "";
    String bucketName = "";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date now = new Date();
    String NowTime = format.format(now);

    @KafkaListener(topics = "${kafka_topic}")
    public void listen(ConsumerRecord<?, ?> record) {
        System.out.println("topic=" + record.topic() + " offset=" + record.offset() + " message=" + record.value());
        redisTemplate.opsForValue().set(String.valueOf(record.value().toString().length()),record.value().toString());
        System.out.println("key>>>>>>>" + record.value().toString().length() + " valuse>>>>>>" + record.value().toString());
        userAllModel userall= new userAllModel();
        userall.setUsername(record.value().toString());
        userall.setDescription("kafka");
        userall.setContent("kafka-content");
        userMapper.insertuser(userall);
        userall.setUsername(record.value().toString()+"-two");
        userMapper.insertusertwo(userall);
        userall.setUsername(record.value().toString()+"-san");
        userMapper.insertusersan(userall);
    }
}
