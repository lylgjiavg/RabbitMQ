package lylgjiavg.step02.limit;

import com.rabbitmq.client.*;

/**
 * @Classname Producer
 * @Description 消费端限流：生产者
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
        
        String exchangeName = "test_QoS_Exchange";
        String routingKey = "QoS.save";
        String msg = "Hello World send QoS Message";
        
        for (int i = 0; i < 4; i++){
            channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
        }
        
    }
    
}
