package RabbitmqHello;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class 备份交换器 {
    private static final String EXCHANG_NAME = "exchange_demo_beifen";
    private static final String ROUTING_KEY = "routing_demo";
    private static final String ROUTING_KEY2 = "routing_demo";
    private static final String QUEUE_NAME = "queue_demo_beifen";
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

        //创建备份交换器
        channel.exchangeDeclare("myAe","fanout",true,false,null);
        channel.queueDeclare("unroutQueue",true,false,false,null);
        channel.queueBind("unroutQueue","myAe","");

        Map<String,Object> argsmap=new HashMap<String,Object>();
        argsmap.put("alternate-exchange","myAe");//作为备份交换器
        //创建一个type="direct",持久化的，非自定删除的交换器
        channel.exchangeDeclare(EXCHANG_NAME,"direct",true,false,argsmap);
        //创建一个持久化，非排他的，非自定删除的队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //String queue = channel.queueDeclare().getQueue(); 创建一个同一个connection层面可用，应用断开连接，就自动删除
        //将交换器与队列通过路由键绑定
        //相当于包裹的目的地
        channel.queueBind(QUEUE_NAME,EXCHANG_NAME,ROUTING_KEY);
        channel.exchangeDeclare(EXCHANG_NAME+"_test","direct",true,false,argsmap);
        //创建一个持久化，非排他的，非自定删除的队列
        channel.queueDeclare(QUEUE_NAME+"_test",true,false,false,null);
        //String queue = channel.queueDeclare().getQueue(); 创建一个同一个connection层面可用，应用断开连接，就自动删除
        //将交换器与队列通过路由键绑定
        //相当于包裹的目的地
        channel.queueBind(QUEUE_NAME+"_test",EXCHANG_NAME+"_test",ROUTING_KEY+"_test");

        //发送错误消息
        channel.basicPublish(EXCHANG_NAME,"",true,MessageProperties.PERSISTENT_TEXT_PLAIN,"备份交换器".getBytes());
        channel.basicPublish(EXCHANG_NAME+"_test","",true,MessageProperties.PERSISTENT_TEXT_PLAIN,"备份交换器".getBytes());


        Thread.sleep(15000);
        channel.close();
        connection.close();

        /*
        *
        * channel error; protocol method: #method<channel.close>(reply-code=406, reply-text=PRECONDITION_FAILED
        * exchange一旦创建就不能改变，而服务器端创建了exchange,客户端也创建一遍就会报错。改变下客户端链接rabbitmq的连接方式就好了

         备份交换器如果和mandatory一起使用，那么mandatory参数无效
         如果设置的备份交换器 不存在，消息丢失
         如果设置的备份交换器没有绑定任何队列，消息丢失
         如果exchang 类型为direct类型，如果路由键匹配失败，那么消息丢失

         * */
    }

}
