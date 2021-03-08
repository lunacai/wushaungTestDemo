package com.example.testhttpdemo.toDB;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class druidDataSource {
    private static DruidDataSource dataSource = null;

    static {
        if (dataSource == null) {
            dataSource = new DruidDataSource();
            //设置连接参数
            dataSource.setUrl("jdbc:mysql://{IP}:3306/canace");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUsername("canal");
            dataSource.setPassword("canal");
            //配置初始化大小、最小、最大
            dataSource.setInitialSize(1);
            dataSource.setMinIdle(1);
            dataSource.setMaxActive(20);
            //连接泄漏监测
            dataSource.setRemoveAbandoned(true);
            dataSource.setRemoveAbandonedTimeout(30);
            //配置获取连接等待超时的时间
            dataSource.setMaxWait(20000);
            //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            dataSource.setTimeBetweenEvictionRunsMillis(20000);
            dataSource.setTestWhileIdle(true);
            dataSource.setTestOnBorrow(true);
        }
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeCon(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
