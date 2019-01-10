package RabbitmqHello;

import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;

public class 设置消息的TTL {
    private static final String EXCHANG_NAME = "消息的TTL_exchange";
    private static final String ROUTING_KEY = "消息的TTL_routingKey";
    private static final String QUEUE_NAME = "消息的TTL_queue";
    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 5672;

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("root");
        factory.setPassword("root");
        Connection connection=factory.newConnection();//创建连接
        Channel channel = connection.createChannel();//创建信道
        //创建一个type="direct",持久化的，非自定删除的交换器

        channel.exchangeDeclare(EXCHANG_NAME,"direct",true,false,null);
        //创建一个持久化，非排他的，非自定删除的队列,队列消息6秒过时
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("x-message-ttl",60000);
//        map.put("x-expires",60000);
//        channel.queueDeclare(QUEUE_NAME,true,false,false,map);
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //String queue = channel.queueDeclare().getQueue(); 创建一个同一个connection层面可用，应用断开连接，就自动删除
        //将交换器与队列通过路由键绑定
        //相当于包裹的目的地
        channel.queueBind(QUEUE_NAME,EXCHANG_NAME,ROUTING_KEY);
        //设置单独消息的过期时间
        AMQP.BasicProperties properties=new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .expiration("6000").build();
        channel.basicPublish(EXCHANG_NAME,ROUTING_KEY,properties,"消息过时".getBytes());
        channel.close();
        connection.close();
    }


}
