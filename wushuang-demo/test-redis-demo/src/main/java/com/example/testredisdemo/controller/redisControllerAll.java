package com.example.testredisdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 集群模式
 */
@RestController
@RequestMapping("/redAll")
public class redisControllerAll {
    static RedissonClient redisson = null;

    static {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress("redis://{IP}:5001")
                .addNodeAddress("redis://{IP}:5002")
                .addNodeAddress("redis://{IP}:5003")
                .addNodeAddress("redis://{IP}:5004")
                .addNodeAddress("redis://{IP}:5005")
                .addNodeAddress("redis://{IP}:5006");

        redisson = Redisson.create(config);
        System.err.println("ok");
    }

    @GetMapping("test")
    public void redisson(String key) {
        RLock lock = redisson.getLock("myLockAll");
        System.err.println("存入," + lock.getName());

        RMap<String, String> map = redisson.getMap("redAll");
        map.put(key, key + 1);
        System.err.println("存入," + map.keySet());
    }
}
