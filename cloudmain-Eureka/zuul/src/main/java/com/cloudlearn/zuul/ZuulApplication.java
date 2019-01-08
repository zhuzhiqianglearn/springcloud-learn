package com.cloudlearn.zuul;

import com.cloudlearn.zuul.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ZuulApplication {

	@Bean
	public AccessFilter accessFilter(){
		return  new AccessFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}
}
