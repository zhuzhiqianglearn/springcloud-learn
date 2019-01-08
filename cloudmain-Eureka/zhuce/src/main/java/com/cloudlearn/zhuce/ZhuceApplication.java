package com.cloudlearn.zhuce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class ZhuceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhuceApplication.class, args);
	}
}
