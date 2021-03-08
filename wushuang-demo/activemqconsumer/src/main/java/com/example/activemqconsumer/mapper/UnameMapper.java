package com.example.activemqconsumer.mapper;

import com.example.activemqconsumer.unameModel;
import org.springframework.stereotype.Component;

@Component
public interface UnameMapper {

    unameModel selectUnameBook(Long id);

    int insertUnameBook(unameModel record);

    int updateUnameBook(unameModel record);

    int delUnameBook(Long id);
}