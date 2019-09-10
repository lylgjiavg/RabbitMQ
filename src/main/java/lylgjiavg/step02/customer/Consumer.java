package lylgjiavg.step02.customer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Classname Consumer
 * @Description 消费端自定义监听：消费端
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

        String exchangeName = "test_Custom_Exchange";
        String routingKey = "Custom.#";
        String queueName = "test_Custom_Queue";
        
        // 声明交换机、声明队列、绑定队列到交换机
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        MyConsumer myConsumer = new MyConsumer(channel);
        channel.basicConsume(queueName, false, myConsumer);
        
    }
    
}
