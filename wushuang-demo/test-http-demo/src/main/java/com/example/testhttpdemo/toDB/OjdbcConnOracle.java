package com.example.testhttpdemo.toDB;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class OjdbcConnOracle {
    private Connection conn = null;
    static String driverClass = "oracle.jdbc.driver.OracleDriver"; //oracle的驱动
    static String url = "jdbc:oracle:thin:@{IP}:1521:ORCL";  //连接oracle路径方式 “”gfs“”是要建立连接的数据库名 1521端口
    static String user = "System";   //user是数据库的用户名
    static String password = "123456";  //用户登录密码
    private static DruidDataSource dataSource = null;

//    public static Connection getconn() {  //为了方便下面的讲解，这里专门建立了一个用于数据库连接的一个方法
//        Connection conn=null;
//        try {
//            //首先建立驱动
//            Class.forName(driverClass);
//            //驱动成功后进行连接
//            conn= DriverManager.getConnection(url, user, password);
//            System.out.println("连接成功");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return conn; //返回一个连接
//    }

    static {
        dataSource = new DruidDataSource();
        //设置连接参数
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
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

//    public List<UserModel> selectAll() {
//        List<UserModel> uselist = new ArrayList<UserModel>();
//        try {
//            conn = getConnection();
//            Statement st = conn.createStatement();
//            //3、创建sql查询语句
//            String querySQL = "SELECT * FROM \"C##CCTEST\".T_USER ";
//            //4、执行sql语句并且换回一个查询的结果集
//            ResultSet rs = st.executeQuery(querySQL);
//            while (rs.next()) {  //循环遍历结果集
//                UserModel use = new UserModel();
//                int id = rs.getInt("id");
//                String userid = rs.getString("userid");
//                String pwd = rs.getString("loginpassword");
//                int isdisable = rs.getInt("isdisable");
//                System.out.println("id=" + id + "--" + "name=" + userid + "--" + "score=" + pwd);
//                use.setID(id);
//                use.setUSERID(userid);
//                use.setLOGINPASSWORD(pwd);
//                use.setISDISABLE(isdisable);
//                uselist.add(use);
//            }
//            st.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return uselist;
//    }
//
//    public boolean selectById(int ids) {
//        try {
//            conn = getConnection();
//            Statement st = conn.createStatement();
//            //3、创建sql查询语句
//            String querySQL = "SELECT * FROM \"C##CCTEST\".T_USER where \"id\"=" + ids;
//            //4、执行sql语句并且换回一个查询的结果集
//            ResultSet rs = st.executeQuery(querySQL);
//            while (rs.next()) {  //循环遍历结果集
//                return true;
//            }
//            st.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public String addUser(UserModel use) {
//        try {
//            conn = getConnection();
//            Statement st = conn.createStatement();
//            String querySQL = "insert into \"C##CCTEST\".T_USER(\"id\",\"userid\",\"loginpassword\",\"isdisable\") VALUES('" + use.getID() + "','" + use.getUSERID() + "','" + use.getLOGINPASSWORD() + "','" + use.getISDISABLE() + "')";
//            System.out.println(querySQL);
//            st.executeUpdate(querySQL);
//            st.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "successful";
//    }
//
//    String addUsernew(UserModel use) {
//        try {
//            conn = getConnection();
//            Statement st = conn.createStatement();
//            String querySQL = "insert into \"C##CCTEST\".C_USER(\"id\",\"userid\",\"loginpassword\",\"isdisable\") VALUES(?,?,?,?)";
//            PreparedStatement std = conn.prepareStatement(querySQL);
//            std.setInt(1, use.getID());
//            std.setString(2, use.getUSERID());
//            std.setString(3, use.getLOGINPASSWORD());
//            std.setInt(4, use.getISDISABLE());
//            std.executeUpdate();
//            st.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "successful";
//    }
//
//    public String updateUser(UserModel use) {
//        try {
//            conn = getConnection();
//            Statement st = conn.createStatement();
//            String querySQL = "update \"C##CCTEST\".T_USER set \"userid\"= '" + use.getUSERID() + "' where \"id\" = " + use.getID();
//            System.out.println(">>>>>>>>>" + querySQL);
//            st.executeUpdate(querySQL);
//            st.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "successful";
//    }
//
//    public String delUser(String ids) {
//        try {
//            conn = getConnection();
//            Statement st = conn.createStatement();
//            String querySQL = "delete from  \"C##CCTEST\".T_USER where \"id\"= " + ids;
//            System.out.println(">>>>>>>>>" + querySQL);
//            st.execute(querySQL);
//            st.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "successful";
//    }
}
