package com.example.onsrocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnsRocketmqApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnsRocketmqApplication.class, args);
		MyMessageConsumer myMessageConsumer = new MyMessageConsumer();
		myMessageConsumer.subscribe();
	}
}
