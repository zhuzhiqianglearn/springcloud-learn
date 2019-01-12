package com.springcloudlearn.rabbitmq.receiver;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;


public class Receiver4 implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("Receiver44444444432432 ChannelAwareMessageListener: 拒绝"+new String(message.getBody())+"----"+message.getMessageProperties().getDeliveryTag());
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
    }
}
