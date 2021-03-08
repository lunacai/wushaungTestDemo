package com.example.testsoftdemo.hbase.impl;

import com.example.testsoftdemo.hbase.City;
import com.example.testsoftdemo.hbase.CityRowMapper;
import com.example.testsoftdemo.hbase.CityService;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: java类作用描述
 * @Author: 破古
 * @CreateDate: 2020/8/4 2:14 下午
 * @UpdateDate: 2020/8/4 2:14 下午
 */
@Service
public class CityServiceImpl implements CityService {
    private static Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private HbaseTemplate hbaseTemplate;
    
    public List<City> query(String startRow, String stopRow) {
        Scan scan = new Scan(Bytes.toBytes(startRow), Bytes.toBytes(stopRow));
        scan.setCaching(5000);
        List<City> dtos = this.hbaseTemplate.find("people_table", scan, new CityRowMapper());
        return dtos;
    }
    
    public City query(String row) {
        City dto = this.hbaseTemplate.get("student", row, new CityRowMapper());
        return dto;
    }
    
    public void saveOrUpdate() throws Exception{
        try {
            List<Mutation> saveOrUpdates = new ArrayList<Mutation>();
            Put put = new Put(Bytes.toBytes("135xxxxxx"));
            put.addColumn(Bytes.toBytes("people"), Bytes.toBytes("name"), Bytes.toBytes("test"));
            saveOrUpdates.add(put);

            this.hbaseTemplate.saveOrUpdates("people_table", saveOrUpdates);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("当前异常 impl======>>>>>"+e);
        }finally {
            this.hbaseTemplate.getConnection().close();
        }

    }

    @Override
    public void add(String tableName) {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "hdp,master01,master02");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        config.set("hbase.rootdir","/hbase");//TODO
        Connection connection = null;
        Admin admin = null;

        try {
            connection = ConnectionFactory.createConnection(config);
            admin = connection.getAdmin();

//            String tableName = "people_table";

            if (!admin.isTableAvailable(TableName.valueOf(tableName))) {
                HTableDescriptor hbaseTable = new HTableDescriptor(TableName.valueOf(tableName));
                hbaseTable.addFamily(new HColumnDescriptor("name"));
                hbaseTable.addFamily(new HColumnDescriptor("contact_info"));
                hbaseTable.addFamily(new HColumnDescriptor("personal_info"));
                admin.createTable(hbaseTable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (admin != null) {
                    admin.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

}
