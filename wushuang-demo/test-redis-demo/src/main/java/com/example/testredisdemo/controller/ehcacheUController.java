package com.example.testredisdemo.controller;

import com.example.clientdemo.RedisMedol;
import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/ehcacheU")
public class ehcacheUController {
    static UserManagedCache<Integer, String> userManagedCache = null;

    static {
        userManagedCache = UserManagedCacheBuilder.newUserManagedCacheBuilder(Integer.class, String.class)
                .build(false);
        userManagedCache.init();
    }

    @PostMapping("/putCache")
    public void putCache(@RequestBody RedisMedol userModel) {
        int ids = userModel.getKey().length();
        userManagedCache.put(ids, userModel.getContent());
        System.out.println("get>>>>>>>>>" + userManagedCache.get(ids));

        userManagedCache.remove(ids);
        System.out.println("get>>>>>>>>>" + userManagedCache.get(ids));

        int num = ids + 1;
        userManagedCache.putIfAbsent(num, "testtt");
        System.out.println("get>>>>>>>>>" + userManagedCache.get(num));

        userManagedCache.replace(num, "wewedddd");
        System.out.println("get>>>>>>>>>" + userManagedCache.get(num));

        userManagedCache.remove(num, "wewedddd");
        System.out.println("get>>>>>>>>>" + userManagedCache.get(num));


    }

    @PostMapping("/CacheAll")
    public void CacheAll(@RequestBody RedisMedol userModel) {
        Set<Integer> idsall = new HashSet<>();
        idsall.add(1);
        idsall.add(2);
        idsall.add(3);
        idsall.add(4);
        Map<Integer, String> map = new HashMap();
        map.put(1, userModel.getContent());
        map.put(2, userModel.getContent() + "11111");
        map.put(3, userModel.getContent() + "22222");
        map.put(4, userModel.getContent() + "33333");
        userManagedCache.putAll(map);
        System.out.println("putAll");
        System.out.println("getAll>>>>>>>" + userManagedCache.getAll(idsall));

        Set<Integer> ids = new HashSet<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        userManagedCache.removeAll(ids);
        System.out.println("removeAll");
        System.out.println("getAll>>>>>>>" + userManagedCache.getAll(idsall));

        userManagedCache.clear();
        System.out.println("getAll>>>>>>>" + userManagedCache.getAll(idsall));

        int idone = userModel.getKey().length();
        userManagedCache.put(idone, userModel.getContent());
        System.out.println("get>>>>>>>>>" +idone+"-----"+ userManagedCache.get(idone));
    }

    @GetMapping("/getCache")
    public void getCache(int key) {
        System.out.println("get>>>>>>>"+userManagedCache.get(key));
    }
}
