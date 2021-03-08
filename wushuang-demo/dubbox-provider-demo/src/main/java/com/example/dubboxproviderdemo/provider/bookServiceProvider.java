//package com.example.dubboxproviderdemo.provider;
//
//
//import com.example.clientdemo.userModel;
//import com.example.dubboxproviderdemo.service.bookService;
//import org.springframework.stereotype.Service;
//
//@Service("userService")
//public class bookServiceProvider implements bookService {
//
//    @Override
//    public userModel getUser() {
//        userModel user = new userModel();
//        user.setKey("szm");
//        user.setContent("123456");
//        System.out.println(user.toString());
//        return user;
//    }
//}