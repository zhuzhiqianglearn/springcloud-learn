package RabbitmqHello;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ReciverHello {
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
        channel.basicQos(1);//设置客户端最多接收未被ack的消息的个数
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("recv message: "+new String(body));
                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //basic.Consume 消费者订阅并接受消息,如果确认消息，那么消息就不消费，
        // 一直存在,多个消费者是轮询消费的（平均分摊），哪个消费者空闲会分摊给他
        // ReciverHello2 睡眠4秒，消费的消息明细那较小
        String s = channel.basicConsume(QUEUE_NAME, consumer);
        //等待回调函数执行完毕之后，关闭字段
        TimeUnit.SECONDS.sleep(60);
        System.out.println(s);
        channel.close();
        connection.close();

    }
}
