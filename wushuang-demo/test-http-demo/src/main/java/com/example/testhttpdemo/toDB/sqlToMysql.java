package com.example.testhttpdemo.toDB;

import com.example.clientdemo.userMedol;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class sqlToMysql {

    public userMedol getList(Connection connection) {
        userMedol user=new userMedol();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from student order by id desc limit 1";
            System.out.println(sql);
            ResultSet rs =statement.executeQuery(sql);
            while (rs.next()) {  //循环遍历结果集
                String id = rs.getString("id");
                String userid = rs.getString("userid");
                String username = rs.getString("username");
                String content = rs.getString("content");
                System.out.println("id=" + id + "--" + "name=" + userid + "--" + "content=" + content);
                user.setId(id);
                user.setUserid(userid);
                user.setUsername(username);
                user.setContent(content);
            }
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean selectById(String ids, Connection connection) {
        try {
            Statement st = connection.createStatement();
            //3、创建sql查询语句
            String querySQL = "SELECT * FROM student where id=" + ids;
            System.out.println("querySQL>>>>>>>>>>>>>>>"+querySQL);
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
    public boolean selectByIdQuery(String ids, Connection connection) {
        try {
            QueryRunner queryRunner = new QueryRunner();
            //3、创建sql查询语句
            String querySQL = "SELECT * FROM student where id=?" ;
            System.out.println("querySQL>>>>>>>>>>>>>>>"+querySQL);
            //4、执行sql语句并且换回一个查询的结果集
//            queryRunner.query(connection,querySQL, new userMedol[]{new userMedol()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void install(userMedol userMedol, Connection connection) {
        PreparedStatement statement = null;
        try {
            String sql = "insert into student(userid,username,content) values(?,?,?)";
            statement = connection.prepareCall(sql);
            statement.setString(1, userMedol.getUserid());
            statement.setString(2, userMedol.getUsername());
            statement.setString(3, userMedol.getContent());
            //5.执行sql语句（执行了几条记录，就返回几）
            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void installtwo(userMedol userMedol, Connection connection) {
        Statement statement = null;
        try {
            String sql = "insert into student(userid,username,content) values(\"" + userMedol.getUserid()
                    + "\",\"" + userMedol.getUsername() + "\",\"" + userMedol.getContent() + "\")";
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String update(userMedol use, Connection conn) {
        try {
            Statement st = conn.createStatement();
            String querySQL = "update student set userid= '" + use.getUserid() + "' where id = " + use.getId();
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
            String querySQL = "delete from  student where id= " + ids;
            System.out.println(">>>>>>>>>" + querySQL);
            st.execute(querySQL);
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "successful";
    }
}
