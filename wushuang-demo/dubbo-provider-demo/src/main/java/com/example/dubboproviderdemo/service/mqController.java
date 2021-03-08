package com.example.dubboproviderdemo.service;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mqController {
    @Value("${rocket_topickey}")
    private String rocketmqTopic;

    private aliMqProducer mqproducer = new aliMqProducer();
    private DefaultMQProducer producer = mqproducer.getProducer();

    @GetMapping("sendMq")
    public String sendMqTopic(String username) {
        Message msg = new Message(rocketmqTopic, "proB", "canaceP002", username.getBytes());
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
}
