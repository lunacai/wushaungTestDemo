package com.example.testhttpdemo.toDB;

import com.example.clientdemo.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class sqlToOracle {

    public List<UserModel> selectAll(Connection conn) {
        List<UserModel> uselist = new ArrayList<UserModel>();
        try {
            Statement st = conn.createStatement();
            //3、创建sql查询语句
            String querySQL = "SELECT * FROM \"C##CCTEST\".T_USER ";
            //4、执行sql语句并且换回一个查询的结果集
            ResultSet rs = st.executeQuery(querySQL);
            while (rs.next()) {  //循环遍历结果集
                UserModel use = new UserModel();
                int id = rs.getInt("id");
                String userid = rs.getString("userid");
                String pwd = rs.getString("loginpassword");
                int isdisable = rs.getInt("isdisable");
                System.out.println("id=" + id + "--" + "name=" + userid + "--" + "score=" + pwd);
                use.setID(id);
                use.setUSERID(userid);
                use.setLOGINPASSWORD(pwd);
                use.setISDISABLE(isdisable);
                uselist.add(use);
            }
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uselist;
    }

    public boolean selectById(int ids, Connection conn) {
        try {
            Statement st = conn.createStatement();
            //3、创建sql查询语句
            String querySQL = "SELECT * FROM \"C##CCTEST\".T_USER where \"id\"=" + ids;
            //4、执行sql语句并且换回一个查询的结果集
            ResultSet rs = st.executeQuery(querySQL);
            while (rs.next()) {  //循环遍历结果集
                return true;
            }
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String addUser(UserModel use, Connection conn) {
        try {
            Statement st = conn.createStatement();
            String querySQL = "insert into \"C##CCTEST\".T_USER(\"id\",\"userid\",\"loginpassword\",\"isdisable\") VALUES('" + use.getID() + "','" + use.getUSERID() + "','" + use.getLOGINPASSWORD() + "','" + use.getISDISABLE() + "')";
            System.out.println(querySQL);
            st.executeUpdate(querySQL);
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "successful";
    }

    public String addUsernew(UserModel use, Connection conn) {
        try {
            Statement st = conn.createStatement();
            String querySQL = "insert into \"C##CCTEST\".C_USER(\"id\",\"userid\",\"loginpassword\",\"isdisable\") VALUES(?,?,?,?)";
            PreparedStatement std = conn.prepareStatement(querySQL);
            std.setInt(1, use.getID());
            std.setString(2, use.getUSERID());
            std.setString(3, use.getLOGINPASSWORD());
            std.setInt(4, use.getISDISABLE());
            std.executeUpdate();
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "successful";
    }

    public String updateUser(UserModel use, Connection conn) {
        try {
            Statement st = conn.createStatement();
            String querySQL = "update \"C##CCTEST\".T_USER set \"userid\"= '" + use.getUSERID() + "' where \"id\" = " + use.getID();
            System.out.println(">>>>>>>>>" + querySQL);
            st.executeUpdate(querySQL);
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "successful";
    }

    public String delUser(String ids, Connection conn) {
        try {
            Statement st = conn.createStatement();
            String querySQL = "delete from  \"C##CCTEST\".T_USER where \"id\"= " + ids;
            System.out.println(">>>>>>>>>" + querySQL);
            st.execute(querySQL);
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "successful";
    }
}
