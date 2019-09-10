package lylgjiavg.step02.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Classname Producer
 * @Description 死信队列：生产端
 * @Date 2019/9/10 15:48
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

        String exchangeName = "test_DLX_Exchange";
        String routingKey = "DLX.save";
        String msg = "Hello World send DLX Message";
        
        channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
        
    }
    
}
