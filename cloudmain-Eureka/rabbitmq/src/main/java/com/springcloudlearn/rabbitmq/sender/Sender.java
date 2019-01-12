package com.springcloudlearn.rabbitmq.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        // this.rabbitTemplate.setConfirmCallback(this);
        //如果没有采用备份服务器，使用 mandatory true(见配置文件)用下main的代码,  备份交换器如果和mandatory一起使用，那么mandatory参数无效
//        this.rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//            @Override
//            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
//                System.out.println("消息发送失败："+message.getMessageProperties().getCorrelationId());
//            }
//        });
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.out.println("HelloSender消息发送失败" + cause + correlationData.toString());
            } else {
//                System.out.println("HelloSender 消息发送成功 ");
            }
        });
        long a=System.currentTimeMillis();
        System.out.println("long a======"+a);
        CorrelationData correlationData=new CorrelationData(a+"");
        for (int i = 0; i < 20; i++) {
            correlationData.setId(i+"");
            this.rabbitTemplate.convertAndSend("hello_exchagne","hello_routingKey", "你好现在是 " + new Date() +"");
        }
    }
}
