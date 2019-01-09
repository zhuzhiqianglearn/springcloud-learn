package RabbitmqHello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import sun.awt.CausedFocusEvent;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

public class SenderHello {
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
        /* 标题1
        * 如果channel绑定另一个队列
        //创建一个持久化，非排他的，非自定删除的队列
        channel.queueDeclare(QUEUE_NAME2,true,false,false,null);
        //绑定另一个queue
        channel.queueBind(QUEUE_NAME2,EXCHANG_NAME,ROUTING_KEY);
        */
        //发送一条持久化的消息
        String message="Hello World";
        for (int i = 0; i < 10; i++) {
            message="Hello World "+i;
            //相当于包裹的邮寄地址，如果与目的地不一致，那么可能会退回也可能会被丢弃
            channel.basicPublish(EXCHANG_NAME,ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        }
        message="Hello World2";
//        channel.basicPublish(EXCHANG_NAME,ROUTING_KEY2, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());

        //关闭资源
        channel.close();;
        connection.close();
   channel.isOpen();
        //交换器 常用的累心共有 fanout，direct，topic,headers 四种
        /*
        * fanout：把所有发送到该交换器的消息路由到所有与交换器绑定的队列中
        * direct：把消息路由到 bingdingKey 和RoutingKey完全匹配的队列中 （标题1 放开，会发现 2个队列都有值）
        *topic： 可以匹配bingdingKey 和RoutingKey  匹配符号 .分隔符  *匹配单个单词  #匹配多个词可以为0 （例如 *.abcd.*;*.*.abcd,abcd.#）
        * headers：依据发送消息时候的headers信息
        * */

        /*判断 connection 和channel 是否开启， 可以用 isOpen ,不建议在生产环境上使用，返回值 依赖于 shutdownCause,可能存在竞争
        public boolean isOpen() {
            Object var1 = this.monitor;
            synchronized(this.monitor) {
                return this.shutdownCause == null;
            }
        }

        exchangeDeclare参数说明：
        exchange ：交换器名称
        type:交换器类型（常用：fanout，direct，topic,headers）
        durable:设置是否持久化，true，持久化到硬盘，重启不会丢失信息
        autoDelete：设置是否自动删除，至少有一个队列连接，之后的队列都自动删除
        internal:设置是否是内置的，ture 表示内置交换器，客户端无法直接发送消息到这个交换器中，只能通过交换器路由到交换器这种方式
        argument(Map):其他一些结构化参数

        queueDeclare参数说明：
        queue:队列名称
        durable：设置是否持久化
        exclusive：设置是否排他，如果为true，其他的连接都不可以创建命名相同的队列，即使设置了持久化，那么断开连接也会自动删除
        autoDelete:设置是否自动删除，至少有一个消费者连接，之后的连接自动删除
        argument(Map):其他一些结构化参数

        exchange和queue都有删除的功能 :
        channel.exchangeDelete( name,isUnused) 删除名称，如果没有在使用的前提下删除
        queue 同上
        queue 有个独有的方法  queuePurge 清空队列内的内容

        queueBing参数说明：
        queue:队列名称
        exchange:交换器名称
        routingKey:用来绑定队列和交换器的路邮键

        channel.basicPublish发送消息参数说明
        exchange:交换器名称
        routingKey:路由键
        props:消息的基本属性集
        byte[] body:消息体
        mandatory和immediate

         * */
    }

}
