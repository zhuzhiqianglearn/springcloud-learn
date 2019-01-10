package RabbitmqHello;

import com.rabbitmq.client.*;

import java.io.IOException;

public class SenderHelloJinJie {
    private static final String EXCHANG_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routing_demo";
    private static final String ROUTING_KEY2 = "routing_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String QUEUE_NAME2 = "queue_demo2";
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
        //创建一个持久化，非排他的，非自定删除的队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //String queue = channel.queueDeclare().getQueue(); 创建一个同一个connection层面可用，应用断开连接，就自动删除
        //将交换器与队列通过路由键绑定
        //相当于包裹的目的地
        channel.queueBind(QUEUE_NAME,EXCHANG_NAME,ROUTING_KEY);
        //第一个参数 mandatory： true:如果exchange不能把消息由路由键到队列，那么将消息返回至身长这，如果为false，直接丢弃
        channel.basicPublish(EXCHANG_NAME,"123",true,MessageProperties.PERSISTENT_TEXT_PLAIN,"错误消息".getBytes());
        //第二个参数 mandatory： true:如果没有消费者，那么消息会被舍弃,rabbitmq 去掉了对mandatory支持，影响队列性能和增加代码复杂性
        // ，官方建议采用TTL和DLX替代
        channel.basicPublish(EXCHANG_NAME,ROUTING_KEY,true,true,MessageProperties.PERSISTENT_TEXT_PLAIN,"错误消息".getBytes());
        //添加监听器监控失败消息
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int i, String s, String s1, String s2, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
                System.out.println(i);
                System.out.println(s);
                System.out.println(s1);
                System.out.println(s2);
                String meesage= new String(bytes);
                System.out.println("Basic.Return返回的结果是： "+meesage);
            }
        });
        Thread.sleep(15000);
        channel.close();
        connection.close();
    }

}
