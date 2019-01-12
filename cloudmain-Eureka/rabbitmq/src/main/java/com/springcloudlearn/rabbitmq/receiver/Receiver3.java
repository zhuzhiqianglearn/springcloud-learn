package com.springcloudlearn.rabbitmq.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


public class Receiver3  implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("Receiver3 ChannelAwareMessageListener: 拒绝"+new String(message.getBody())+"----"+message.getMessageProperties().getDeliveryTag());
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
    }
}
