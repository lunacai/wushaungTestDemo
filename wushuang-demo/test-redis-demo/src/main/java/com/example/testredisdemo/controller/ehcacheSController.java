package com.example.testredisdemo.controller;

import com.example.clientdemo.RedisMedol;
import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/ehcachesan")
public class ehcacheSController {
    static PersistentCacheManager persistentCacheManager = null;
    static Cache<Integer, String> cache = null;

    static {
        persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence("/Users/lunacaishulie.io/ehcache" + File.separator + "myDatatwo"))
                .withCache("threeTieredCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(Integer.class, String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(10, EntryUnit.ENTRIES)  //堆
                                        .offheap(1, MemoryUnit.MB)    //堆外
                                        .disk(20, MemoryUnit.GB)      //磁盘
                        )
                ).build(true);
        cache = persistentCacheManager.getCache("threeTieredCache", Integer.class, String.class);
    }

    @PostMapping("/putCache")
    public void putCache(@RequestBody RedisMedol userModel) {
        int ids = userModel.getKey().length();
        cache.put(ids, userModel.getContent());
        System.out.println("get>>>>>>>>>" + cache.get(ids));

        cache.remove(ids);
        System.out.println("get>>>>>>>>>" + cache.get(ids));

        int num = ids + 1;
        cache.putIfAbsent(num, "testtt");
        System.out.println("get>>>>>>>>>" + cache.get(num));

        cache.replace(num, "wewedddd");
        System.out.println("get>>>>>>>>>" + cache.get(num));

        cache.remove(num, "wewedddd");
        System.out.println("get>>>>>>>>>" + cache.get(num));

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
        cache.putAll(map);
        System.out.println("putAll");
        System.out.println("getAll>>>>>>>" + cache.getAll(idsall));

        Set<Integer> ids = new HashSet<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        cache.removeAll(ids);
        System.out.println("removeAll");
        System.out.println("getAll>>>>>>>" + cache.getAll(idsall));
        cache.clear();
        System.out.println("getAll>>>>>>>" + cache.getAll(idsall));

        int idone = userModel.getKey().length();
        cache.put(idone, userModel.getContent());
        System.out.println("get>>>>>>>>>" +idone+"-----"+ cache.get(idone));
    }

    @GetMapping("/getCache")
    public void getCache(int key) {
        System.out.println("get>>>>>>>"+cache.get(key));
    }
}
