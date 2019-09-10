package lylgjiavg.step02.customer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Classname Producer
 * @Description 消费端自定义监听：生产端
 * @Date 2019/9/8 10:50
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
        
        String exchangeName = "test_Custom_Exchange";
        String routingKey = "Custom.save";
        String msg = "Hello World send Custom Message";
        
        for (int i = 0; i < 4; i++){
            channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
        }
        
    }
    
}
