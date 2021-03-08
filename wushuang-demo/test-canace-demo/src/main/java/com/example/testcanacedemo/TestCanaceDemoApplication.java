package com.example.testcanacedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:dubbox-consumer.xml")
public class TestCanaceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCanaceDemoApplication.class, args);
	}

}
