package RabbitmqHello;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ReciverHello2 {
    private static final String EXCHANG_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routing_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 5672;

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
                if(!new String(body).contains("1")){
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }else{
                    System.out.println(envelope.getDeliveryTag());
                    //拒绝消息，如果false，那么直接在内存中直接删除，如果设置成true，那么他会重新编号，进行下一次发送
                    channel.basicReject(envelope.getDeliveryTag(),false);
                }
            }
        };
        //basic.Consume 消费者订阅并接受消息,如果确认消息，那么消息就不消费，一直存在
        String s = channel.basicConsume(QUEUE_NAME, false,consumer);
        //等待回调函数执行完毕之后，关闭字段
        TimeUnit.SECONDS.sleep(15);
        System.out.println(s);
        channel.close();
        connection.close();

    }
}
