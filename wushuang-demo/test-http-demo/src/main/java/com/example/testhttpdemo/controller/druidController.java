package com.example.testhttpdemo.controller;

import com.example.clientdemo.UserModel;
import com.example.clientdemo.userMedol;
import com.example.testhttpdemo.toDB.OjdbcConnOracle;
import com.example.testhttpdemo.toDB.druidDataSource;
import com.example.testhttpdemo.toDB.sqlToMysql;
import com.example.testhttpdemo.toDB.sqlToOracle;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.List;


@RestController
@RequestMapping("/druid")
public class druidController {

    public static druidDataSource druidDb = new druidDataSource();
    public static sqlToMysql mysql = new sqlToMysql();
    private OjdbcConnOracle connOracle = new OjdbcConnOracle();
    public static sqlToOracle oracle = new sqlToOracle();

    @PostMapping("test")
    public String addDruid(@RequestBody userMedol userMedol) {
        Connection connection = druidDb.getConnection();
        if (mysql.selectById(userMedol.getId(), connection)) {
            mysql.update(userMedol, connection);
        } else {
            mysql.install(userMedol, connection);
        }
        druidDb.closeCon(connection);
        return "successfull";
    }

    @PostMapping("testtwo")
    public String addDruidMysql(@RequestBody userMedol userMedol) {
        Connection connection = druidDb.getConnection();
        if (mysql.selectById(userMedol.getId(), connection)) {
            mysql.update(userMedol, connection);
        } else {
            mysql.installtwo(userMedol, connection);
        }
        druidDb.closeCon(connection);
        return "successfull";
    }

    @GetMapping("delTest")
    public String delDruidMysql() {
        Connection connection = druidDb.getConnection();
        userMedol user = mysql.getList(connection);
        System.out.println("del ids>>>>>>>>>>>" + user.getId());
        mysql.delUser(user.getId(), connection);
        druidDb.closeCon(connection);
        return "successfull";
    }


    @PostMapping("/addOjdbc")
    public String DruidOjdbc(String username, String types) {
        System.out.println("types>>>>>>>>>>>>>>" + types);
        Connection connection = connOracle.getConnection();
        int num = username.length();
        UserModel use = new UserModel();
        use.setID(num);
        use.setUSERID(username);
        use.setLOGINPASSWORD("123456");
        use.setISDISABLE(1);
        if (types.equals("1")) {
            if (oracle.selectById(num, connection)) {
                oracle.updateUser(use, connection);
            } else {
                oracle.addUser(use, connection);
            }
        } else {
            List<UserModel> uselist = oracle.selectAll(connection);
            UserModel user = uselist.get(0);
            System.out.println("del ids>>>>>>>>>>>" + user.getID());
            oracle.delUser(String.valueOf(user.getID()), connection);
        }
        connOracle.closeCon(connection);
        return "successful";
    }

}
