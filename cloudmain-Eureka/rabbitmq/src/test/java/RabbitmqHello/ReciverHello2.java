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
        factory.setUsername("guest");
        factory.setPassword("guest");
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
                    //参数：true，说明如果他被确认，那么编号envelope.getDeliveryTag()比他小的也确认
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
                else{
                    //参数：true，发送给空闲的消费者，fasle，发送给之前相同的消费者，默认是true
//                    channel.basicRecover(true);
                    //参数：true，重新生成编号，发送给消费者， false：直接删除
                    channel.basicReject(envelope.getDeliveryTag(),true);
                }
            }
        };
        //basic.Consume 消费者订阅并接受消息,如果确认消息，那么消息就不消费，一直存在
        String s = channel.basicConsume(QUEUE_NAME, false,consumer);
        //等待回调函数执行完毕之后，关闭字段
        TimeUnit.SECONDS.sleep(60);
        System.out.println(s);
        channel.close();
        connection.close();

    }
}
