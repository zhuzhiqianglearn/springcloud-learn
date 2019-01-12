package RabbitmqHello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class 生产者消息事务机制 {
    private static final String EXCHANG_NAME = "exchange_生产者事务机制";
    private static final String ROUTING_KEY = "routing_生产者事务机制";
    private static final String QUEUE_NAME = "queue_生产者事务机制";
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
        channel.basicPublish(EXCHANG_NAME,ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,"生产者消息确认".getBytes());
        //发送一个 事务的消息
        try {
            //开启事务
            channel.txSelect();
            Long l=System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                channel.basicPublish(EXCHANG_NAME,ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,"生产者消息确认".getBytes());
                channel.txCommit();
            }
            System.out.println(System.currentTimeMillis()-l);
            //让事务回滚
//            int x=1/0;
            //提交事务
        }catch (Exception e){
            e.printStackTrace();
            Thread.sleep(5000);
            //事务回滚
            channel.txRollback();
        }
        finally {
            channel.close();
            connection.close();
        }
    }
    /*
      使用事务，优点是我们可以保证 每条消息都能发送到rabbit服务中，失败的消息进行回滚，缺点是 事务功能太消耗性能

     */

}
