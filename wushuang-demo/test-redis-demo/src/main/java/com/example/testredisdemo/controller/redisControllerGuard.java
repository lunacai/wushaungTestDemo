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
///**
// * 哨兵模式
// */
//@RestController
//@RequestMapping("/redGuard")
//public class redisControllerGuard {
//    static RedissonClient redisson = null;
//
//    static {
//        Config config = new Config();
//        config.useSentinelServers()
//                .setMasterName("mymaster")
//                .addSentinelAddress("redis://{IP}:26379")
//                .addSentinelAddress("redis://{IP}:26380", "redis://{IP}:26381");
//
//        redisson = Redisson.create(config);
//        System.err.println("ok");
//    }
//
//    @GetMapping("test")
//    public void redisson(String key) {
//        RLock lock = redisson.getLock("myLockGuard");
//        System.err.println("存入," + lock.getName());
//
//        RMap<String, String> map = redisson.getMap("redGuard");
//        map.put(key, key + 1);
//        System.err.println("存入," + map.keySet());
//    }
//
//    @GetMapping("test1")
//    public void test() {
//        System.err.println("存入" );
//    }
//}
