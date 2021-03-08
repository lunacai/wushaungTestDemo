package com.example.testcanacedemo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.clientdemo.RedisMedol;
import com.example.dubboxproviderdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/dubboxConsumer")
public class UserController {
    //此注解正是dubbox调用远程服务的关键注解
    @Reference
    private UserService userService;
    @Autowired
    JedisPool jedisPool;

    @PostMapping("/setName")
    public RedisMedol setName(@RequestBody RedisMedol user) {
        return userService.setUser(user);
    }

    @GetMapping("/jedistest")
    public void jedisTest(String key) {
        Jedis jedis=jedisPool.getResource();
        System.err.println("设置内容," + key);
        jedis.set("jedisOne"+key,key);

        String str = jedis.get("jedisOne"+key);
        System.err.println("获取内容," + str);

        jedis.close();

    }

}