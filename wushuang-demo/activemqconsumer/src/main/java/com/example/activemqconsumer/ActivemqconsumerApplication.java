package com.example.activemqconsumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.activemqconsumer")
public class ActivemqconsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivemqconsumerApplication.class, args);
	}

}
