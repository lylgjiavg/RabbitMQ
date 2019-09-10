package lylgjiavg.step02.nautoack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Classname Consumer
 * @Description 消费端ACK：消费者
 * @Date 2019/9/8 10:48
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

        String exchangeName = "test_Ack_Exchange";
        String routingKey = "Ack.#";
        String queueName = "test_Ack_Queue";
        
        // 声明交换机、声明队列、绑定队列到交换机
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
        
        // autoAck设置为false
        channel.basicConsume(queueName, false, new MyConsumer(channel));
        
    }
    
}
