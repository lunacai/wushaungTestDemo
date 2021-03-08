package com.example.testhttpdemo.controller;

import com.example.clientdemo.userMedol;
import com.example.testhttpdemo.toDB.dbcp2DataSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dbcp2")
public class dbcp2Controller {

    public static dbcp2DataSource dbcpDb = new dbcp2DataSource();

    @PostMapping("test")
    public String addDruid(@RequestBody userMedol userMedol) {
        dbcpDb.install(userMedol);
        return "successfull2222";
    }
}
