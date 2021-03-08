package com.example.testsoftdemo.ons;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Properties;

public class CloudMQUtil {
    /**
     * 获取消息的 Producer
     *
     * @return Producer
     */
    public static Producer getProducer() {
        Properties properties = new Properties();
        // AccessKeyId 阿里云身份验证，在阿里云用户信息管理控制台获取。
        properties.put(PropertyKeyConst.AccessKey,onsConfigParams.ACCESS_KEY);
        // AccessKeySecret 阿里云身份验证，在阿里云用户信息管理控制台获取。
        properties.put(PropertyKeyConst.SecretKey, onsConfigParams.SECRET_KEY);
        //设置发送超时时间，单位毫秒。
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置 TCP 接入域名，进入控制台的实例详情页面的 TCP 协议客户端接入点区域查看。
        properties.put(PropertyKeyConst.NAMESRV_ADDR, onsConfigParams.NAMESRV_ADDR);
        Producer producer = ONSFactory.createProducer(properties);
        // 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可。
        producer.start();
        return producer;
    }
}
