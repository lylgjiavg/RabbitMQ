package lylgjiavg.step01.topicexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @Classname Consumer
 * @Description RabbitMQ的Topic模式:消费者
 * @Date 2019/9/3 16:02
 * @Created by Jiavg
 */
public class Consumer {

    public static void main(String[] args) throws Exception{

        // 1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        // 2.通过连接工厂获得连接
        Connection connection = connectionFactory.newConnection();

        // 3.通过连接创建信道
        Channel channel = connection.createChannel();

        String exchangeName = "test_Topic_Exchange";
        String exchangeType = "topic";
        String routeKey = "test.*";
        String queueName = "test_Topic_Queue";
        
        //  4.声明交换机 
        channel.exchangeDeclare(exchangeName, exchangeType, true, false,false,null);
        
        // 5.声明队列
        channel.queueDeclare(queueName, true, false, false, null);

        // 6.创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        
        // 7.绑定队列到交换机上(利用topic模式的routeKey)
        channel.queueBind(queueName, exchangeName, routeKey);

        // 8.设置参数
        channel.basicConsume(queueName, true, queueingConsumer);
        
        // 8.接收消息
        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();

            byte[] body = delivery.getBody();
            String msg = new String(body);
            System.out.printf(msg);
        }
        
    }
    
}
