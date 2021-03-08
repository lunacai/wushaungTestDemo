package com.example.dubboxproviderdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:dubbox-provider.xml")
public class DubboxProviderDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboxProviderDemoApplication.class, args);
	}

}
