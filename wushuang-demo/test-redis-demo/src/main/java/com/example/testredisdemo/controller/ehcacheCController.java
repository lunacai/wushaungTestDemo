package com.example.testredisdemo.controller;

import com.example.clientdemo.RedisMedol;
import org.ehcache.PersistentUserManagedCache;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.core.spi.service.LocalPersistenceService;
import org.ehcache.impl.config.persistence.DefaultPersistenceConfiguration;
import org.ehcache.impl.config.persistence.UserManagedPersistenceContext;
import org.ehcache.impl.persistence.DefaultLocalPersistenceService;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/ehcachetwo")
public class ehcacheCController {
    static PersistentUserManagedCache cache = null;

    static {
        LocalPersistenceService persistenceService = new DefaultLocalPersistenceService(new DefaultPersistenceConfiguration(
                new File("/Users/lunacaishulie.io/ehcache", "myUserData")));
        cache = (PersistentUserManagedCache) UserManagedCacheBuilder.newUserManagedCacheBuilder(Long.class, String.class)
                .with(new UserManagedPersistenceContext("cacheName", persistenceService))
                .withResourcePools(ResourcePoolsBuilder.newResourcePoolsBuilder()
                        .heap(10L, EntryUnit.ENTRIES)
                        .disk(10L, MemoryUnit.MB, true)).build(true);
    }

    @PostMapping("/putCache")
    public void putCache(@RequestBody RedisMedol userModel) {
        Long ids = Long.valueOf(userModel.getKey().length());
        cache.put(ids, userModel.getContent());
        System.out.println("get>>>>>>>>>" + cache.get(ids));

        cache.remove(ids);
        System.out.println("get>>>>>>>>>" + cache.get(ids));

        Long num = ids + 1;
        cache.putIfAbsent(num, "testtt");
        System.out.println("get>>>>>>>>>" + cache.get(num));

        cache.replace(num, "wewedddd");
        System.out.println("get>>>>>>>>>" + cache.get(num));

        cache.remove(num, "wewedddd");
        System.out.println("get>>>>>>>>>" + cache.get(num));

    }

    @PostMapping("/CacheAll")
    public void CacheAll(@RequestBody RedisMedol userModel) {
        Set<Long> idsall = new HashSet<>();
        idsall.add(1L);
        idsall.add(2L);
        idsall.add(3L);
        idsall.add(4L);
        Map<Long, String> map = new HashMap();
        map.put(1L, userModel.getContent());
        map.put(2L, userModel.getContent() + "11111");
        map.put(3L, userModel.getContent() + "22222");
        map.put(4L, userModel.getContent() + "33333");
        cache.putAll(map);
        System.out.println("putAll");
        System.out.println("getAll>>>>>>>" + cache.getAll(idsall));

        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        cache.removeAll(ids);
        System.out.println("removeAll");
        System.out.println("getAll>>>>>>>" + cache.getAll(idsall));
        cache.clear();
        System.out.println("getAll>>>>>>>" + cache.getAll(idsall));

        Long idone = Long.valueOf(userModel.getKey().length());
        cache.put(idone, userModel.getContent());
        System.out.println("get>>>>>>>>>" +idone+"-----"+ cache.get(idone));
    }

    @GetMapping("/getCache")
    public void getCache(Long key) {
        System.out.println("get>>>>>>>"+cache.get(key));
    }
}
