package com.example.testredisdemo;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.clientdemo.userMedol;

import java.sql.Connection;
import java.sql.Statement;

public class druidDataSource {

    public static DruidDataSource getDbConnect() {
        try {
            DruidDataSource dataSource = null;
            if (dataSource == null) {
                dataSource = new DruidDataSource();
                //设置连接参数
                dataSource.setUrl("jdbc:mysql://{IP}:3306/canace");
                dataSource.setDriverClassName("com.mysql.jdbc.Driver");
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
            return dataSource;
        } catch (Exception e) {
            return null;
        }
    }


//    public static void install(userMedol userMedol) {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        try {
//            connection = getDbConnect().getConnection();
//            String sql = "insert into student(userid,username,content) values(?,?,?)";
//
//            statement = connection.prepareCall(sql);
//            statement.setString(1, userMedol.getUserid());
//            statement.setString(2, userMedol.getUsername());
//            statement.setString(3, userMedol.getContent());
//            //5.执行sql语句（执行了几条记录，就返回几）
//            statement.executeUpdate();
//            statement.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (Exception e) {
//
//                }
//
//            }
//        }
//    }
    public static void install(userMedol userMedol) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getDbConnect().getConnection();
            String sql = "insert into student(userid,username,content) values(\"" + userMedol.getUserid()
                    + "\",\"" + userMedol.getUsername() + "\",\"" + userMedol.getContent() + "\")";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {

                }

            }
        }
    }
}
