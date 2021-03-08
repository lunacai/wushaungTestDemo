package com.example.testhttpdemo.toDB;

import com.example.clientdemo.userMedol;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HikariCPDataSourceMysql {

    private static HikariDataSource ds;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://{IP}:3306/canace?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&useSSL=false");
        config.setUsername("canal");
        config.setPassword("canal");
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("useLocalSessionState", true);
        config.addDataSourceProperty("rewriteBatchedStatements", true);
        config.addDataSourceProperty("cacheResultSetMetadata", true);
        config.addDataSourceProperty("cacheServerConfiguration", true);
        config.addDataSourceProperty("elideSetAutoCommits", true);
        config.addDataSourceProperty("maintainTimeStats", false);
        ds = new HikariDataSource(config);
    }


    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void install(userMedol userMedol) {
        Connection connection = getConnection();
        Statement statement = null;
        try {
            String sql = "insert into student(userid,username,content) values(\"" + userMedol.getUserid()
                    + "\",\"" + userMedol.getUsername() + "\",\"" + userMedol.getContent() + "\")";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("sql>>>>>>>>>>>>>>>");
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOther(userMedol user) {
        //创建QueryRunner 对象
        QueryRunner queryRunner = new QueryRunner();
        //获取Connection连接
        Connection connection = null;
        String sql = "insert into student(userid,username,content) values(?,?,?)";
        try {
            connection = getConnection();
            queryRunner.update(connection, sql,user.getUserid(), user.getUsername(),user.getContent() );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
