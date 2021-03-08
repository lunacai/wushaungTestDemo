package com.example.testredisdemo.controller;

import com.example.clientdemo.userMedol;
import com.example.testredisdemo.druidDataSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/druid")
public class druidController {

    public static druidDataSource druidDb = new druidDataSource();

    @PostMapping("test")
    public String redisson(@RequestBody userMedol userMedol) {
        druidDb.install(userMedol);
        return "successfull";
    }
}
