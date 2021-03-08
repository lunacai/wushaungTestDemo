package com.example.dubboproviderdemo.service.impl;


import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.example.clientdemo.RedisMedol;
import com.example.dubboproviderdemo.service.UserService;
import com.example.dubboproviderdemo.service.aliMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class userServiceImpl implements UserService {

    @Value("${rocket_topickey}")
    private String rocketmqTopic;

    private aliMqProducer mqproducer = new aliMqProducer();

    public String sendMqTopic(String username) {
        Message msg = new Message(rocketmqTopic, "proA", "canaceP001", username.getBytes());
        DefaultMQProducer producer = mqproducer.getProducer();
        SendResult sendResult = null;
        try {
            sendResult = producer.send(msg);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("消息id:" + sendResult.getMsgId() + "," + "发送状态:" + sendResult.getSendStatus());
        return sendResult.getMsgId();

    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String addUser(RedisMedol userModel) {
        System.out.println("redis set>>>>>>>>>>key：" + userModel.getKey() + " and value=" + userModel.getContent());
        redisTemplate.opsForValue().set(userModel.getKey(), userModel.getContent());
        return "set key successful";
    }

    public String hello() {
        redisTemplate.opsForValue().set("canace", "deee");
        return "set key successful";
    }
}
