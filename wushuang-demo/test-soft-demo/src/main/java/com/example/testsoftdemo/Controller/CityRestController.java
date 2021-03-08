package com.example.testsoftdemo.Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.testsoftdemo.hbase.City;
import com.example.testsoftdemo.hbase.CityService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CityRestController {
    private static Logger logger = LoggerFactory.getLogger(CityRestController.class);
    public static Configuration configuration; // 管理Hbase的配置信息
    public static Connection connection; // 管理Hbase连接
    public static Admin admin; // 管理Hbase数据库的信息

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/api/city/save", method = RequestMethod.GET)
    public City save() {
        try {
            cityService.saveOrUpdate();
        } catch (Exception e) {
            System.out.println("异常信息======>>>>>>" + e);
            e.printStackTrace();
        } finally {
            City city = new City();
            city.setAge(1);
            return city;
        }
    }

    @RequestMapping(value = "/api/city/get", method = RequestMethod.GET)
    public City getCity() {
        return cityService.query("zhangsan");
    }

    @RequestMapping(value = "/api/addAll", method = RequestMethod.GET)
    public String addData() throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String NowTime = format.format(now);
        try {
            init();
            logger.info("init===admin===>>>" + admin);
            String colF[] = {"score"};
            createTable("student3", colF); // 建表
            insertData("student3", "zhangsan", "score", "English", "69");
            String aa = getData("PT_student3", "zhangsan", "score", "Computer");
            logger.info("获取到的信息是=======>>>>>>>>>>" + aa + "<<<<<<<<<<======");
            return aa;
        } catch (Exception e) {
            logger.info("85行异常信息----->>>>>>>" + e);
            return null;
        } finally {
            close();
        }
    }


    @RequestMapping(value = "/api/search", method = RequestMethod.GET)
    public void searchData(String table, String key, String family, String col) throws IOException {
        try {
            init();
            String aa = getAsyncData(table, key, family, col);
            logger.info("========查询到的数据是====>>>>>>>" + aa);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    @RequestMapping(value = "/api/put", method = RequestMethod.GET)
    public void putData(String table, String key, String family, String col) throws IOException {
        String value = "tankTest" + System.currentTimeMillis();
        try {
            init();
            insertData(table, key, family, col, value);
            logger.info("========插入的数据是====>>>>>>>" + value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("========走到关闭====>>>>>>>");
            close();
        }
    }

    @RequestMapping(value = "/api/createTable", method = RequestMethod.GET)
    public void create(String myTableName, String[] colFamily) throws IOException {
        init();
        createTable(myTableName, colFamily);
        logger.info("createTable完成----->>>>>>>>>");
        close();
    }


    public static void init() {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.rootdir", "hdp://hdp:9000/hbase");
        configuration.set("hbase.zookeeper.quorum", "hdp,master01,master02"); // 设置zookeeper节点
        configuration.set("hbase.zookeeper.quorum", "hdp"); // 设置zookeeper节点
        configuration.set("hbase.zookeeper.property.clientPort", "2181"); // 设置zookeeper节点
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTable(String myTableName, String[] colFamily) throws IOException {
        TableName tableName = TableName.valueOf(myTableName);
        if (admin.tableExists(tableName)) {
            System.out.println("Table exists");
        } else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for (String str : colFamily) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(str);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(hTableDescriptor);
        }
    }

    // 添加单元格数据
    /*
     * @param tableName 表名
     * @param rowKey 行键
     * @param colFamily 列族
     * @param col 列限定符
     * @param val 数据
     * @thorws Exception
     * */
    public static void insertData(String tableName, String rowKey, String colFamily, String col, String val) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        try {
            logger.info("insertData传过来的表名是----->>>>>>>>>" + tableName);
            Put put = new Put(rowKey.getBytes());
            put.addColumn(colFamily.getBytes(), col.getBytes(), val.getBytes());
            table.put(put);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("-------174行有异常----->>>>>>>>>" + e);
        }
        table.close();
        close();
    }

    //浏览数据
    /*
     * @param tableName 表名
     * @param rowKey 行
     * @param colFamily 列族
     * @param col 列限定符
     * @throw IOException
     * */
    public static String getData(String tableName, String rowKey, String colFamily, String col) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        get.addColumn(colFamily.getBytes(), col.getBytes());
        Result result = table.get(get);
        table.close();
        return "获取到的信息是---->>>>>>." + new String(result.getValue(colFamily.getBytes(), col == null ? null : col.getBytes()));
    }

    public static String getAsyncData(String tableName, String rowKey, String colFamily, String col) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(rowKey.getBytes());
        get.addColumn(colFamily.getBytes(), col.getBytes());
        Result result = table.get(get);
        table.close();
        return "获取到的信息是---->>>>>>." + new String(result.getValue(colFamily.getBytes(), col == null ? null : col.getBytes()));
    }

    // 操作数据库之后，关闭连接
    public static void close() {
        try {
            if (admin != null) {
                admin.close(); // 退出用户
            }
            if (null != connection) {
                connection.close(); // 关闭连接
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logger.info("---------->>>>>>>>>>>>>>>>已走到关闭完成<<<<<<<<<<<<<<---------");
        }
    }
}