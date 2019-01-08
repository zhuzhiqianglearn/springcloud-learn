package com.cloud.core2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Core2Application {

	public static void main(String[] args) {
		SpringApplication.run(Core2Application.class, args);
	}
}
