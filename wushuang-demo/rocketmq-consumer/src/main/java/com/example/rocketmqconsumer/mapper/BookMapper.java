package com.example.rocketmqconsumer.mapper;

import com.example.rocketmqconsumer.bookModel;
import org.springframework.stereotype.Component;

@Component
public interface BookMapper {

    bookModel selectBook(Long id);

    int insertBook(bookModel record);

    int updateBook(bookModel record);

    int delBook(Long id);
}