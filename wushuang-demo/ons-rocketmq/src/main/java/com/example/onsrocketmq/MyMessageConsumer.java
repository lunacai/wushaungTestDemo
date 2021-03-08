package com.example.onsrocketmq;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Date;
import java.util.Properties;

public class MyMessageConsumer {
    /**
     * 订阅消息
     */
    public void subscribe() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, onsConfigParams.CONSUMER_ID);
        // AccessKeyId 阿里云身份验证，在阿里云用户信息管理控制台获取。
        properties.put(PropertyKeyConst.AccessKey, onsConfigParams.ACCESS_KEY);
        // AccessKeySecret 阿里云身份验证，在阿里云用户信息管理控制台获取。
        properties.put(PropertyKeyConst.SecretKey, onsConfigParams.SECRET_KEY);
        //设置发送超时时间，单位毫秒。
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置 TCP 接入域名，进入控制台的实例详情页面的 TCP 协议客户端接入点区域查看。
        properties.put(PropertyKeyConst.NAMESRV_ADDR, onsConfigParams.NAMESRV_ADDR);

        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe(onsConfigParams.TOPIC, "*", new MyMessageListener());//此处可以写父类messageListener但是必须实现方法这里就是用到了上面写的myMessageListener
        consumer.start();
        System.out.println(onsConfigParams.CONSUMER_ID + " is running @" + new Date());
    }
}
