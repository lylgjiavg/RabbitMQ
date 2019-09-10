package lylgjiavg.step01.topicexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Classname Producer
 * @Description RabbitMQ的Topic模式:生产者
 * @Date 2019/9/3 15:56
 * @Created by Jiavg
 */
public class Producer {

    public static void main(String[] args) throws Exception{

        // 1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        // 2.通过连接工厂获得连接
        Connection connection = connectionFactory.newConnection();

        // 3.通过连接创建信道
        Channel channel = connection.createChannel();
        
        String msg1 = "Hello World Topic Exchange with routeKey for test.hello";
        String msg2 = "Hello World Topic Exchange with routeKey for test.hello.world";
        String msg3 = "Hello World Topic Exchange with routeKey for hello.world";
        
        String routeKey1 = "test.hello";
        String routeKey2 = "test.hello.world";
        String routeKey3 = "hello.world";
        
        String exchangeName = "test_Topic_Exchange";
        
        // 4.发送消息
        channel.basicPublish(exchangeName, routeKey1, null, msg1.getBytes());
        channel.basicPublish(exchangeName, routeKey2, null, msg2.getBytes());
        channel.basicPublish(exchangeName, routeKey3, null, msg3.getBytes());
        
        // 5.关闭资源
        channel.close();
        connection.close();
        
    }
    
}
