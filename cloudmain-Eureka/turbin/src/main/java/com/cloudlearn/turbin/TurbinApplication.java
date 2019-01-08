package com.cloudlearn.turbin;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTurbine
public class TurbinApplication {
	@Bean
	public ServletRegistrationBean getServlet(){

		HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();

		ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);

		registrationBean.setLoadOnStartup(1);

		registrationBean.addUrlMappings("/actuator/hystrix.stream");

		registrationBean.setName("HystrixMetricsStreamServlet");


		return registrationBean;
	}
	public static void main(String[] args) {
		SpringApplication.run(TurbinApplication.class, args);
	}
}
