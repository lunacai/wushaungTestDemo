package com.example.dubboproviderdemo.service;


import com.example.clientdemo.RedisMedol;

public interface UserService {

    String sendMqTopic(String username);

    String addUser(RedisMedol userModel);

    String hello();
}
