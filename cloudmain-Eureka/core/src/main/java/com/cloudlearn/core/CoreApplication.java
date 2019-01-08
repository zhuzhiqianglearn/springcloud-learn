package com.cloudlearn.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/*通过@EnableEurekaServer注解启动一个服务注册中心，提供给其他应用进行对话
 * 默认情况下服务注册中心也会将自己作为客户端尝试注册自己，所以我们应该禁止，只需在配置文件中修改即可（pom.xml有详细说明）:
 * eureka.client.register-with-eureka=false
 * eureka.client.fetch-registry=false
 * */
@EnableEurekaServer
@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}
}
