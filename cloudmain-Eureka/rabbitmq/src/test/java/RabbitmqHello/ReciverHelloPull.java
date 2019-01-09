package RabbitmqHello;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ReciverHelloPull {
    private static final String EXCHANG_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routing_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 5672;
    //RabbitMq不支持队列层面的广播消费，需要二次开发
    public static void main(String[] args) throws Exception{
        Address[] addresses=new Address[]{new Address(IP_ADDRESS,PORT)};
        ConnectionFactory factory=new ConnectionFactory();
        factory.setUsername("root");
        factory.setPassword("root");
        //与生产者创建连接不同
        Connection connection=factory.newConnection(addresses);//创建连接
        Channel channel = connection.createChannel();//创建信道
        GetResponse getResponse = channel.basicGet(QUEUE_NAME, false);
        System.out.println(new String(getResponse.getBody()));
        channel.basicAck(getResponse.getEnvelope().getDeliveryTag(),false);
        channel.close();
        connection.close();
        /*
          消费模式分为两种：推和拉 推：Basic.Consume 拉：Basic.Get
          推模式：
            channel.basicConsume(QUEUE_NAME, autoAck,consumer); 说明
            QUEUE_NAME：消费队列的名称
            autoAck:设置是否自动确认，建议设置成false，即不自动确认

            执行顺序按照上诉执行
            建议一个channel对应一个消费者，如果对应多个，那么可能存在阻塞问题，影响效率
       */
    }
}
