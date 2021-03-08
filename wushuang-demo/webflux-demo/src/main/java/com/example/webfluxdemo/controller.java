package com.example.webfluxdemo;

import com.pamirs.pradar.Pradar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/city")
public class controller {
    @Autowired
    private CityHandler cityHandler;

    @GetMapping(value = "/getCity")
    public Mono<String> findCityById(String key) {
        System.out.println("Pradar>>>>>>>>>>>>>>>>"+ Pradar.isClusterTest());
        return cityHandler.findCityById(key);
    }

    @PostMapping("/saveCity")
    public Mono<String> saveCity(@RequestBody City city) {
        System.out.println("Pradar>>>>>>>>>>>>>>>>"+ Pradar.isClusterTest());
        return cityHandler.save(city);
    }
}
