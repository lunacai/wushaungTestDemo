package com.example.webfluxdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CityRepository {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String save(City city) {
        System.out.println("set>>>>>>>>>>"+city.getCityName());
        redisTemplate.opsForValue().set(city.getCityName(),city.getDescription());
        return "save successful";
    }

    public String findCityById(String key) {
        System.out.println("get>>>>>>>>>>"+redisTemplate.opsForValue().get(key));
        return redisTemplate.opsForValue().get(key);
    }
}