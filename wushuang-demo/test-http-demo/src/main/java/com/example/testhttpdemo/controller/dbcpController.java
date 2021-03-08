package com.example.testhttpdemo.controller;

import com.example.clientdemo.userMedol;
import com.example.testhttpdemo.toDB.dbcpDataSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dbcp")
public class dbcpController {

    public static dbcpDataSource dbcpDb = new dbcpDataSource();

    @PostMapping("test")
    public String addDruid(@RequestBody userMedol userMedol) {
        dbcpDb.install(userMedol);
        return "successfull";
    }
}
