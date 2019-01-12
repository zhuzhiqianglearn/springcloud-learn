//package com.springcloudlearn.rabbitmq;
//
//import com.rabbitmq.client.AMQP;
//import com.springcloudlearn.rabbitmq.sender.Sender;
//import org.springframework.amqp.core.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Collection;
//import java.util.Iterator;
//
//@SpringBootApplication
//@RestController
//public class RabbitmqApplication {
//	@Autowired
//	private Sender sender;
//	@Autowired
//	private AmqpTemplate amqpTemplate;
//	//注册队列
//	@Bean
//	public Queue helloQueue(){
//		return  new Queue("hello_queue",true,false,false,null);
//	}
//	@Bean
//	public DirectExchange helloExchange(){
//		return  new DirectExchange("hello_exchagne",true,false,null);
//	}
//
//	@Bean
//	public Binding helloBinding(Queue queue, DirectExchange directExchange){
//		return BindingBuilder.bind(queue).to(directExchange).with("hello_routingKey");
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(RabbitmqApplication.class, args);
//	}
//	@RequestMapping("/sender")
//	public void sender(){
//	       sender.send();
//	}
//	@RequestMapping("/sender2")
//	public void sender2(){
//		for (int i = 0; i < 10; i++) {
//			amqpTemplate.convertAndSend("hello_exchagne","hello_routingKey","简单的情景实现 "+i);
//
//		}
//	}
//
//}
//
