package com.example.dubboproviderdemo.service;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

public class aliMqProducer {

    public static DefaultMQProducer getProducer() {
        DefaultMQProducer producer = new DefaultMQProducer("canaceGroupNametwo");
        try {
            producer.setNamesrvAddr("{IP}:9876");
            /**
             *默认情况下，一台服务器只能启动一个Producer或Consumer实例，所以如果需要在一台服务器启
             *动多个实例，需要设置实例的名称
             */
            producer.setInstanceName("producer");
            producer.setSendMsgTimeout(30000);//发送消息超时
            producer.setRetryTimesWhenSendFailed(1);//发送失败后，重试几次
            /**
             * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
             * 注意：切记不可以在每次发送消息时，都调用start方法
             */
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return producer;
    }

}
