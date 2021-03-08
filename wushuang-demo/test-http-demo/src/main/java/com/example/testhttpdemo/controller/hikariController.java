package com.example.testhttpdemo.controller;

import com.example.clientdemo.UserModel;
import com.example.clientdemo.userMedol;
import com.example.testhttpdemo.toDB.HikariCPDataSourceMysql;
import com.example.testhttpdemo.toDB.HikariCPDataSourceOracle;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hikaricp")
public class hikariController {
    public static HikariCPDataSourceMysql hikariDb= new HikariCPDataSourceMysql();
    public static HikariCPDataSourceOracle hikariDboracle= new HikariCPDataSourceOracle();

    @PostMapping("test")
    public String addDruid(@RequestBody userMedol userMedol) {
        System.out.println("start>>>>>>>>>>>>>>>");
//        hikariDb.install(userMedol);
        hikariDb.setOther(userMedol);
        return "successfull";
    }

    @PostMapping("test1")
    public String addDruid1(@RequestBody UserModel userMedol) {
        System.out.println("start>>>>>>>>>>>>>>>");
//        System.out.println(userMedol.getID());
//        System.out.println(userMedol.getUSERID());
//        System.out.println(userMedol.getLOGINPASSWORD());
//        System.out.println(userMedol.getISDISABLE());
        hikariDboracle.install(userMedol);
        hikariDboracle.setOther(userMedol);
        return "successfull";
    }
}
