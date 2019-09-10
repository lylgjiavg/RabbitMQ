package lylgjiavg.step02.nautoack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname Producer
 * @Description 消费端ACK：生产者
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
        
        String exchangeName = "test_Ack_Exchange";
        String routingKey = "Ack.save";

        for (int i = 0; i < 4; i++){
            String msg = "Hello World send Ack Message:" + i;
            
            Map<String, Object> headers = new HashMap<>();
            headers.put("num", i);

            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .headers(headers)
                    .build();
            
            channel.basicPublish(exchangeName, routingKey,true, properties, msg.getBytes());
        }
    }
}
