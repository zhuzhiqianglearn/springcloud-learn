package RabbitmqHello;

import com.rabbitmq.client.*;

import java.io.IOException;

public class 生产者消息确认机制 {
    private static final String EXCHANG_NAME = "exchange_生产者确认机制";
    private static final String ROUTING_KEY = "routing_生产者确认机制";
    private static final String QUEUE_NAME = "queue_生产者确认机制";
    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 5672;

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("root");
        factory.setPassword("root");
        Connection connection=factory.newConnection();//创建连接
        Channel channel = connection.createChannel();//创建信道
        //创建一个类型为direct，持久,不自动删除，不是内置的交换器
        channel.exchangeDeclare(EXCHANG_NAME,"direct",true,false,false,null);
        //创建一个持久，非排他，非自动删除的队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //将队列，交换器和路由键绑定
        channel.queueBind(QUEUE_NAME,EXCHANG_NAME,ROUTING_KEY);
        channel.confirmSelect();//将信道标记为 确认机制
        Long l=System.currentTimeMillis();
        for (int i = 0; i <10000 ; i++) {
            channel.basicPublish(EXCHANG_NAME,ROUTING_KEY,false, MessageProperties.PERSISTENT_TEXT_PLAIN,"生产者消息确认机制".getBytes());

            if(channel.waitForConfirms()){
//                System.out.println("发送成功");
            }
        }
        System.out.println(System.currentTimeMillis()-l);

//        channel.addReturnListener(new ReturnListener() {
//            @Override
//            public void handleReturn(int i, String s, String s1, String s2, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
//                System.out.println("消息发送失败：  "+new String(bytes));
//            }
//        });
        Thread.sleep(5000);
        channel.close();
        connection.close();
    }
    /*
       事务机制和确认机制，两者不能共存，
       使用两种机制，建议和mandatory或者备份减缓其一起使用，提高消息传输的可靠性
       确认机制有两种使用方法：同步和异步  同步qps比事务机制提升不明显 异步缺点是在basic。ack超时的情况下，
        会将消息全部重发，经常丢失消息的时候，效率不升反将
     */

}
