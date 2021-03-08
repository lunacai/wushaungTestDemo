package com.example.rabbitmqdemo;

import com.example.clientdemo.RedisMedol;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@RabbitListener(queues = "canaceRabbitQueue", group = "canaceRabit")
public class rabbitMQConsumer {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 消息消费
     *
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void receive(String msg) {
        System.out.println("[Queue] recieved message: " + msg);
        String ids = String.valueOf(msg.length());
        RedisMedol user = new RedisMedol();
        user.setKey(ids);
        user.setContent(msg);
        Query query = new Query(Criteria.where("id").is(ids));
        RedisMedol userd = mongoTemplate.findOne(query, RedisMedol.class);
        if (userd!= null) {
            Update update = new Update().set("username", user.getContent());
            mongoTemplate.updateFirst(query, update, RedisMedol.class);
            System.out.println("updata monogdb successful");
        } else {
            mongoTemplate.save(user);
            System.out.println("insert monogdb successful");
        }
    }
}