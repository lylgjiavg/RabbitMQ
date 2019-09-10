package lylgjiavg.step02.ttl;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Classname Producer
 * @Description TTL队列/消息：生产者
 * @Date 2019/9/10 14:38
 * @Created by Jiavg
 */
public class Producer {

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        
        String exchangeName = "test_Topic_Exchange";
        String routingKey = "test.ttl";
        String msg = "Hello World send TTL Message";
        
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                // 设置消息的超时时间
                .expiration("10000")
                .build();
        
        channel.basicPublish(exchangeName, routingKey, properties, msg.getBytes());
        
    }
    
}
