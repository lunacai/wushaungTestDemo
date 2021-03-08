package com.example.testredisdemo.controller;

import com.example.clientdemo.RedisMedol;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.impl.config.persistence.CacheManagerPersistenceConfiguration;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/ehcache")
public class ehcacheController {
    private static String cahcheName = "myCache";
    static CacheManager cacheManager = null;
    static Cache<Long, String> myCache = null;

    static {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(new CacheManagerPersistenceConfiguration(new File("/Users/lunacaishulie.io/ehcache" + File.separator + "myData")))
                .build();
        cacheManager.init();
        /**创建一个缓存，设定其配置 。 可以给 CacheConfiguration 绑定 缓存时效的配置 **/
        myCache = cacheManager.createCache(cahcheName,
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                        ResourcePoolsBuilder.heap(10)  //堆内大小
                                .offheap(2, MemoryUnit.MB)  //堆外大小
                                .disk(1, MemoryUnit.GB)   //硬盘缓存大小
                )
        );
    }

    @PostMapping("/putCache")
    public void putCache(@RequestBody RedisMedol userModel) {
        Long ids = Long.valueOf(userModel.getKey().length());
        myCache.put(ids, userModel.getContent());
        System.out.println("get>>>>>>>>" + myCache.get(ids));

        Long pkey=ids+1;
        myCache.putIfAbsent(pkey,"testcase");
        System.out.println("get>>>>>>>>" + myCache.get(pkey));

        pkey=pkey+1;
        System.out.println(myCache.replace(pkey,"testcase","tescddd"));
        System.out.println("get>>>>>>>>" + myCache.get(pkey));
        System.out.println(myCache.replace(pkey,"tescddde","testcase"));
        System.out.println("get>>>>>>>>" + myCache.get(pkey));

        myCache.remove(pkey,"testcase");
        System.out.println("get>>>>>>>>" + myCache.get(pkey));

    }

    @PostMapping("/delCache")
    public void delCache(@RequestBody RedisMedol userModel) {
        Long ids = Long.valueOf(userModel.getKey().length());
        myCache.put(ids, userModel.getContent());
        System.out.println("put>>>>>>>>" + userModel.getContent());
        System.out.println("get>>>>>>>>" + myCache.get(ids));
        myCache.clear();
        System.out.println("clear");
        System.out.println("get>>>>>>>>" + myCache.get(ids));

        myCache.put(ids, userModel.getContent() + "sdsdsds");
        System.out.println("put>>>>>>>>" + userModel.getContent());
        System.out.println("get>>>>>>>>" + myCache.get(ids));
        myCache.remove(ids);
        System.out.println("remove");
        System.out.println("get>>>>>>>>" + myCache.get(ids));

    }

    @PostMapping("/repCache")
    public void repCache(@RequestBody RedisMedol userModel) {
        Long ids = Long.valueOf(userModel.getKey().length());
        myCache.put(ids, userModel.getContent());
        System.out.println("put");
        System.out.println("get>>>>>>>" + myCache.get(ids));

        myCache.replace(ids, userModel.getContent() + "sdsd");
        System.out.println("replace");
        System.out.println("get>>>>>>>" + myCache.get(ids));
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
        myCache.putAll(map);
        System.out.println("putAll");
        System.out.println("getAll>>>>>>>" + myCache.getAll(idsall));

        Set<Long> ids = new HashSet<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        myCache.removeAll(ids);
        System.out.println("removeAll");
        System.out.println("getAll>>>>>>>" + myCache.getAll(idsall));

    }
    @PostMapping("/CacheAllTwo")
    public void CacheAllTwo(@RequestBody RedisMedol userModel) {
        Set<Long> idsall = new HashSet<>();
        idsall.add(1L);
        idsall.add(2L);
        idsall.add(3L);
        idsall.add(4L);
        Map<Long, String> map = new HashMap();
        map.put(1L, userModel.getContent());
        map.put(2L, userModel.getContent() + "wwwww");
        map.put(3L, userModel.getContent() + "eeeee");
        map.put(4L, userModel.getContent() + "sssss");
        myCache.putAll(map);
        System.out.println("putAll");
        System.out.println("getAll>>>>>>>" + myCache.getAll(idsall));

        ArrayList<Long> a = new ArrayList<>();
        a.add(1L);
        a.add(3L);
        Iterator<Long> it = a.iterator();
        while (it.hasNext()) {
            Long t = it.next();
            myCache.remove(t);
        }
        System.out.println("getAll>>>>>>>" + myCache.getAll(idsall));

    }

    @GetMapping("/getCache")
    public void getCache(Long key) {
        System.out.println("get>>>>>>>"+myCache.get(key));
    }
}
