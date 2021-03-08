//package com.example.testredisdemo.controller;
//
//import org.redisson.Redisson;
//import org.redisson.api.RLock;
//import org.redisson.api.RMap;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/redOne")
//public class RedisController {
//
//    static RedissonClient redisson = null;
//
//    static {
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://{IP}:6379").setPassword("123456").setDatabase(2);
//        redisson = Redisson.create(config);
//        System.err.println("ok");
//    }
//
//    @GetMapping("test")
//    public void redisson(String key) {
//        RLock lock = redisson.getLock("myLKOnly");
//        System.err.println("存入," + lock.getName());
//
//        RMap<String, String> map = redisson.getMap("redOne");
//        map.put(key, key + 1);
//        System.err.println("存入," + map.keySet());
//    }
//}
