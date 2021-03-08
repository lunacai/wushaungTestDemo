package com.example.testsoftdemo.Controller;

import com.example.clientdemo.RedisMedol;
import com.example.dubboproviderdemo.service.UserService;
import com.example.dubboproviderdemo.service.providerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dubbo")
public class dubboController {

    @Autowired
    private UserService userService;

    @Autowired
    private providerService providerService;

    @GetMapping("/sendToDubbo")
    public String sendToDubbo(String username) {
        RedisMedol user = new RedisMedol();
        user.setKey(String.valueOf(username.length()));
        user.setContent("dubbo_" + username);
        System.out.println("Http Provider>>>>>>>" + userService.addUser(user));
        return "Send to Provider successfully";
    }

    @GetMapping("/sendToRabbit")
    public String sendToRabbit(String username) {
        System.out.println("Http Provider>>>>>>>" + providerService.toRabbitmq(username));
        return "Send to Provider successfully";
    }
}
