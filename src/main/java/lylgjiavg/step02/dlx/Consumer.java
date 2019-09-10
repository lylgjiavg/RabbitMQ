package lylgjiavg.step02.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lylgjiavg.step02.limit.MyConsumer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname Consumer
 * @Description 死信队列：消费端
 * @Date 2019/9/10 15:48
 * @Created by Jiavg
 */
public class Consumer {

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
        String queueName = "test_DLX_Queue";
        
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "dlx.exchange");
        // 这个arguments属性要设置到声明队列上
        channel.queueDeclare(queueName, true,false,false, arguments);
        channel.queueBind(queueName, exchangeName, routingKey);
        
        // 要进行死信队列的声明
        channel.exchangeDeclare("dlx.exchange", "topic", true,false,null);
        channel.queueDeclare("dlx.queue", true, false,false,null);
        channel.queueBind("dlx.queue", "dlx.exchange", "#");
        
        channel.basicConsume(queueName, true, new MyConsumer(channel));
        
    }
    
}
