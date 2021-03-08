package com.example.testsoftdemo.hbase;

import java.util.List;

public interface CityService {

    List<City> query(String startRow, String stopRow);

    public City query(String row);

    void saveOrUpdate() throws Exception;

    void add(String tableName);

}
