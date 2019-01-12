package com.springcloudlearn.rabbitmq;

import com.springcloudlearn.rabbitmq.sender.Sender;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Rabbitmq2Application {
	@Autowired
	private Sender sender;
	@Autowired
	private RabbitTemplate amqpTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Rabbitmq2Application.class, args);
	}
	@RequestMapping("/sender")
	public void sender(){
	       sender.send();
	}
	@RequestMapping("/sender2")
	public void sender2(){
		for (int i = 0; i < 10; i++) {
			amqpTemplate.convertAndSend("hello_exchagne","","简单的情景实现 "+i);

		}
	}

}

