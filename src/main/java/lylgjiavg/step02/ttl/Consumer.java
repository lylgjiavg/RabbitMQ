package lylgjiavg.step02.ttl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname Consumer
 * @Description TTL队列/消息：消费者
 * @Date 2019/9/10 14:36
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

        String exchangeName = "test_TTL_Exchange";
        String routingKey = "TTL.#";
        String queueName = "test_TTL_Queue";
        
        // 创建声明队列所用到的参数集合,并设置其超时时间x-message-ttl为10000(10秒)
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 10000);
        
        // 声明交换机、声明队列、绑定队列到交换机
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, arguments);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 创建消费者、消费队列
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            byte[] body = delivery.getBody();

            System.out.printf("消费端:" + new String(body));
        }
        
    }
    
}
