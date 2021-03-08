package com.example.rocketmqconsumer;

import com.example.rocketmqconsumer.mapper.BookMapper;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
public class consumerP {
    /**
     * 消费者实体对象
     */
    private DefaultMQPushConsumer consumerMq;
    /**
     * 消费者组
     */
    public static final String CONSUMER_GROUP = "test_pp_consumer";
    private String address = "{IP}:9876";
    private String topic = "canacerocketmq";
    private List<String> userlist = new ArrayList<String>();


    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 通过构造函数 实例化对象
     */
    public consumerP() throws MQClientException {
        consumerMq = new DefaultMQPushConsumer(CONSUMER_GROUP);
        consumerMq.setNamesrvAddr(address);
        //消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        consumerMq.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        //订阅主题和 标签（ * 代表所有标签)下信息
        consumerMq.subscribe(topic, "*");
        consumerMq.setVipChannelEnabled(false);
        // //注册消费的监听 并在此监听中消费信息，并返回消费的状态信息
        consumerMq.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            // msgs中只收集同一个topic，同一个tag，并且key相同的message
            // 会把不同的消息分别放置到不同的队列中
            try {
                for (Message msg : msgs) {
                    //消费者获取消息 这里只输出 不做后面逻辑处理
                    String body = new String(msg.getBody(), "utf-8");
                    System.out.println("Consumer BBB-获取消息-主题topic为={}, 消费消息为=" + msg.getTopic() +"----"+ body);
                    insterDB(userlist, body);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumerMq.start();
        System.out.println("消费者B 启动成功=======");
    }


    public void insterDB(List<String> userlist, String name) {
        System.out.println("userList B>>>>>>>>>"+userlist.toString());
        if (!userlist.contains(name)) {
            System.out.println("name>>>>>>>>>"+name);
            bookModel book = new bookModel();
            book.setBookname(name);
            book.setBookdes("dubbo-"+name);
            bookMapper.insertBook(book);
            userlist.add(name);
            int key = name.length();
            System.out.println("redis set>>>>>>>>>>key：" + key + " and value=" + name);
            redisTemplate.opsForValue().set(String.valueOf(key), name);
        }
    }
}