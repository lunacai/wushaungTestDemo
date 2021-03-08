package com.example.testhttpdemo.toDB;

import com.example.clientdemo.UserModel;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HikariCPDataSourceOracle {

    private static HikariDataSource ds;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:oracle:thin:@{IP}:1521:ORCL");
        config.setUsername("System");
        config.setPassword("123456");
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

    public void install(UserModel use) {
        Connection connection = getConnection();
        try {
            Statement st = connection.createStatement();
            String querySQL = "insert into \"C##CCTEST\".T_USER(\"id\",\"userid\",\"loginpassword\",\"isdisable\") VALUES('"
                    + use.getID() + "','" + use.getUSERID() + "','" + use.getLOGINPASSWORD() + "','" + use.getISDISABLE() + "')";
            st.executeUpdate(querySQL);
            System.out.println("sql>>>>>>>>>>>>>>>"+querySQL);
            st.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOther(UserModel user) {
        //创建QueryRunner 对象
        QueryRunner queryRunner = new QueryRunner();
        //获取Connection连接
        Connection connection = null;
        String sql = "insert into \"C##CCTEST\".T_USER(\"id\",\"userid\",\"loginpassword\",\"isdisable\") values(?,?,?,?)";
        try {
            connection = getConnection();
            System.out.println(user.getID());
            queryRunner.update(connection, sql, user.getID(), user.getUSERID(), user.getLOGINPASSWORD(),user.getISDISABLE());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
