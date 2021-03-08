package com.example.testsoftdemo.Controller;

import com.example.testsoftdemo.rocketmq.mqProducer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rocketmq")
public class rocketmqController {
    private mqProducer mqproducer = new mqProducer();
    private DefaultMQProducer producer = mqproducer.getProducer();

    @Value("${sbootitframe.rocketmq.topic1}")
    private String topictwo;

    @GetMapping("/sendToRocketmq")
    public String sendToRocketmq(String username) {
        Message msg = new Message(topictwo, "white", username.getBytes());
        try {
            SendResult sendResult = producer.send(msg);
            String result = sendResult.getMsgId();
            System.out.println("消息id:" + result + "," + "发送状态:" + sendResult.getSendStatus());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
}
