package com.example.testhttpdemo.toDB;

import com.example.clientdemo.userMedol;
import org.apache.commons.dbcp.BasicDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

public class dbcpDataSource {

    public static DataSource getDbConnect() {
        try {
            DataSource dataSource = null;
            if (dataSource == null) {
                BasicDataSource basicDataSource = new BasicDataSource();
                //设置连接参数
                basicDataSource.setUrl("jdbc:mysql://{IP}:3306/canace");
                basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
                basicDataSource.setUsername("canal");
                basicDataSource.setPassword("canal");
                //配置初始化大小、最小、最大
                basicDataSource.setInitialSize(1);
                basicDataSource.setMinIdle(1);
                basicDataSource.setMaxActive(20);
                dataSource=basicDataSource;
            }
            return dataSource;
        } catch (Exception e) {
            return null;
        }
    }

    public void install(userMedol userMedol) {
        DataSource dataSource =getDbConnect();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            String sql = "insert into student(userid,username,content) values(\"" + userMedol.getUserid()
                    + "\",\"" + userMedol.getUsername() + "\",\"" + userMedol.getContent() + "\")";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
