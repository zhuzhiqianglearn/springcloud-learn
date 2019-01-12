package com.springcloudlearn.rabbitmq.RabbitMqConfig;


import com.springcloudlearn.rabbitmq.receiver.Receiver3;
import com.springcloudlearn.rabbitmq.receiver.Receiver4;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.ListenerContainerAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Configuration
public class RCOnfig {

    @Bean
	public Queue helloQueue(){
		return  new Queue("hello_queue",true,false,false,null);
	}
    @Bean
    public Queue helloQueueReturn(){
        return  new Queue("hello_queue_return",true,false,false,null);
    }
	@Bean
	public DirectExchange helloExchange(){
        Map<String,Object> argsmap=new HashMap<String,Object>();
        argsmap.put("alternate-exchange","hello_exchagne_return");//作为备份交换器
		return  new DirectExchange("hello_exchagne",true,false,argsmap );
	}

	@Bean
    public FanoutExchange fanoutExchange(){
        return  new FanoutExchange("hello_exchagne_return",true,false,null);
    }

	@Bean
	public Binding helloBinding(Queue helloQueue, DirectExchange directExchange){
		return BindingBuilder.bind(helloQueue).to(directExchange).with("hello_routingKey");
	}
    @Bean
    public Binding helloReturnBinding(Queue helloQueueReturn, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(helloQueueReturn).to(fanoutExchange);
    }
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("127.0.0.1:5672");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true); //必须要设置
        return connectionFactory;
    }
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }
    @Bean
    public Receiver3 receiver3(){
        return new Receiver3();
    }    @Bean
    public Receiver4 receiver4(){
        return new Receiver4();
    }
    //单个消费者监听器
    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(helloQueue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(receiver3());
        container.setMessageListener(receiver4());
        return container;
    }

}
