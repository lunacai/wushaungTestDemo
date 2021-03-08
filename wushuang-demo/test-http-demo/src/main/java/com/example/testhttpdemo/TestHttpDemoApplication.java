package com.example.testhttpdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:elastic-job.xml")
public class TestHttpDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestHttpDemoApplication.class, args);
	}

}
