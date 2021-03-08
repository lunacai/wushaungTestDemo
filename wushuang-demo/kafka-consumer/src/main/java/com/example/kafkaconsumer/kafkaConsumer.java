package com.example.kafkaconsumer;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.example.clientdemo.userAllModel;
import com.example.kafkaconsumer.mapper.UserMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class kafkaConsumer {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;
    String endpoint = "oss-cn-beijing.aliyuncs.com";
    String accessKeyId = "";
    String accessKeySecret = "";
    String bucketName = "";

    @KafkaListener(topics = "${kafka_user}")
    public void listen(ConsumerRecord<?, ?> record) {
        System.out.println("topic=" + record.topic() + " offset=" + record.offset() + " message=" + record.value());
        redisTemplate.opsForValue().set(String.valueOf(record.value().toString().length()),record.value().toString());
        System.out.println("key>>>>>>>" + record.value().toString().length() + " valuse>>>>>>" + record.value().toString());
//        userMapper.selectuser(239787L);
        userAllModel userall= new userAllModel();
        userall.setUsername(record.value().toString());
        userall.setDescription("use");
        userall.setContent("use-content");
        userMapper.insertuser(userall);

        String url= "/opt/canace/wsDemo/kafka/start.sh";
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName,
                        "tank/ossFile", new File(url));
        System.out.println("------当前我要的key是------>>>>>"+putObjectRequest.getKey());
        ossClient.putObject(putObjectRequest);
    }
}
