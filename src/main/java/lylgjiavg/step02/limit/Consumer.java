package lylgjiavg.step02.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Classname Consumer
 * @Description 消费端限流：消费者
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

        String exchangeName = "test_QoS_Exchange";
        String routingKey = "QoS.#";
        String queueName = "test_QoS_Queue";
        
        // 声明交换机、声明队列、绑定队列到交换机
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
        
        // 限流方式
        channel.basicQos(0, 1, false);

        // autoAck设置为false
        channel.basicConsume(queueName, false, new MyConsumer(channel));
        
    }
    
}
