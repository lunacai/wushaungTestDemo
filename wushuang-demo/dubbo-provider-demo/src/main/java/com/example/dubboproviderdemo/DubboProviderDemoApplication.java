package com.example.dubboproviderdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:dubbo-provider.xml")
public class DubboProviderDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderDemoApplication.class, args);
    }

}
