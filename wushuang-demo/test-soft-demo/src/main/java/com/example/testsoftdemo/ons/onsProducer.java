package com.example.testsoftdemo.ons;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class onsProducer {

    private final static Logger logger = LoggerFactory.getLogger(onsProducer.class);

    public String sendMessage(String topic, String tag, String msgStr) {
        StringBuilder logsb = new StringBuilder("send OnsMQ Msg:");
        Message msg = getInstance(topic, tag, msgStr);
        System.out.println("msg>>>>>>>>>>:"+msg);
        String messageId = "";
        Producer producer = CloudMQUtil.getProducer(); //你申请的producerId
        msg.setKey("canace_" + msgStr.length());
        SendResult sendResult = producer.send(msg);
        messageId = sendResult.getMessageId();
        return messageId;
    }

    private static Message getInstance(String topic, String tag, String body) {
        if (body.equals("")||body == null)
            body = "";
        Message msg = new Message(topic, tag, body.getBytes());
        return msg;
    }
}
