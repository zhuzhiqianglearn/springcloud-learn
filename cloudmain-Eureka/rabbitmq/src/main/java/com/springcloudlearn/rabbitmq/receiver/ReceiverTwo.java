package com.springcloudlearn.rabbitmq.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "hello_queue")
public class ReceiverTwo {
    @RabbitHandler
    public void process(String hello, Channel channel, Message message) throws IOException {
        channel.basicQos(6);
        System.out.println("Receiver2: 拒绝"+hello+"----"+message.getMessageProperties().getDeliveryTag());
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
    }

}
