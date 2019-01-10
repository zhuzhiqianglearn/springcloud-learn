package RabbitmqHello;

import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;

public class 死信队列 {
    private static final String EXCHANG_NAME = "死信队列_exchange";
    private static final String ROUTING_KEY = "死信队列_routingKey";
    private static final String QUEUE_NAME = "死信队列_queue";
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
        //创建死信队列
        channel.exchangeDeclare(EXCHANG_NAME,"direct",true,false,null);
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANG_NAME,ROUTING_KEY);

        //创建发送数据源
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("x-message-ttl",50000);//设置消息的过期时间
        map.put("x-dead-letter-exchange",EXCHANG_NAME);//设置死信的exchange 交换器
        map.put("x-dead-letter-routing-key",ROUTING_KEY);//设置死信的 路由键
        channel.exchangeDeclare(EXCHANG_NAME+"_数据源","direct",true,false,null);
        channel.queueDeclare(QUEUE_NAME+"_数据源",true,false,false,map);
        channel.queueBind(QUEUE_NAME+"_数据源",EXCHANG_NAME+"_数据源",ROUTING_KEY+"_数据源");

        //生产消息
        channel.basicPublish(EXCHANG_NAME+"_数据源",ROUTING_KEY+"_数据源",MessageProperties.PERSISTENT_TEXT_PLAIN,"死信队列数据".getBytes());
        channel.close();
        connection.close();
    }


}
