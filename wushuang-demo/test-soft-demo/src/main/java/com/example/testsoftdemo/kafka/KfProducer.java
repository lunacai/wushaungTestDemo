package com.example.testsoftdemo.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Component
public class KfProducer {
    private static KafkaProducer<String, String> producer = null;

    private static KafkaProducer initConfig(String brokerList) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<String, String>(properties);
        return producer;
    }

    /**
     * 异步发送消息
     *
     * @param content
     */
    public void sendAsyn(String brokerList, String topic, String content) {
        producer = KfProducer.initConfig(brokerList);
        //消息实体
        ProducerRecord<String, String> record = null;
        record = new ProducerRecord<String, String>(topic, content + (int) (10 * (Math.random())));
        //发送消息
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (null != e) {
                    System.out.println("send error" + e.getMessage());
                } else {
                    System.out.println(String.format("offset:%s,partition:%s", recordMetadata.offset(), recordMetadata.partition()));
                }
            }
        });
        //异步发送消息时必须要flush,否则发送不成功，不会执行回调函数
        producer.flush();
        producer.close();
    }

    /**
     * 同步发送消息
     *
     * @param content
     */
    public static void sendSyn(String brokerList,String topic, String content) {
        producer = KfProducer.initConfig(brokerList);
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "key1", content);
        //同步发送方式,get方法返回结果
        RecordMetadata metadata = null;
        try {
            metadata = (RecordMetadata) producer.send(record).get();
            System.out.println("broker返回消息发送信息" + metadata);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}