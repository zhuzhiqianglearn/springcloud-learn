package com.springcloudlearn.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.springcloudlearn.rabbitmq.sender.Sender;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;

@SpringBootApplication
@RestController
public class RabbitmqApplication {
	@Autowired
	private Sender sender;
@Bean
public Queue helloQueue(){
	return  new Queue("hello");
}
	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);
	}
	@RequestMapping("/sender")
	public void sender(){
	       sender.send();
	}

}

