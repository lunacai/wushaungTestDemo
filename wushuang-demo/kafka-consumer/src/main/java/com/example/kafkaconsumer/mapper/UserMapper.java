package com.example.kafkaconsumer.mapper;

import com.example.clientdemo.userAllModel;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    userAllModel selectuser(Long id);

    userAllModel selectuserone(Long id);

    int insertuser(userAllModel record);

    int updateuser(userAllModel record);

    int deluser(Long id);

    userAllModel selectusertwo(Long id);

    int insertusertwo(userAllModel record);

    int updateusertwo(userAllModel record);

    int delusertwo(Long id);

    userAllModel selectusersan(Long id);

    int insertusersan(userAllModel record);

    int updateusersan(userAllModel record);

    int delusersan(Long id);
}