//package com.example.testredisdemo.controller;
//
//import org.redisson.Redisson;
//import org.redisson.api.*;
//import org.redisson.config.Config;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 主从模式
// */
//@RestController
//@RequestMapping("/redMaster")
//public class redisControllerMaster {
//    static RedissonClient redisson = null;
//
//    static {
//        Config config = new Config();
//        config.useMasterSlaveServers().setMasterAddress("redis://{IP}:9001")
//                .addSlaveAddress("redis://{IP}:9002", "redis://{IP}:9003");
//        redisson = Redisson.create(config);
//        System.err.println("ok");
//    }
//
//    @GetMapping("test")
//    public void redisson(String key) {
//        RLock lock = redisson.getLock("myLockMaster");
//        System.err.println("存入," + lock.getName());
//
//        RMap<String, String> map = redisson.getMap("redMaster");
//        map.put(key, key + 1);
//        System.err.println("存入," + map.keySet());
//    }
//}
