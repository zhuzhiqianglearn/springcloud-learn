package com.springcloudlearn.rabbitmq.receiver;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "hello_queue")
public class Receiver {
    @RabbitHandler
    public void process(String hello,Channel channel, Message message) throws IOException {
        channel.basicQos(8);
        System.out.println("Receiver1 确认: "+hello+"-----"+message.getMessageProperties().getDeliveryTag());
        //参数：true，说明如果他被确认，那么编号envelope.getDeliveryTag()比他小的也确认

        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
