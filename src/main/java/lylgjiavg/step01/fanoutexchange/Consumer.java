package lylgjiavg.step01.fanoutexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @Classname Consumer
 * @Description RabbitMQ的Fanout模式:消费者
 * @Date 2019/9/3 17:14
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
        
        String exchangeName = "test_Fanout_Exchange";
        String exchangeType = "fanout";
        
        String queueName = "test_Fanout_Queue";
        String routeKey = "";
        
        channel.exchangeDeclare(exchangeName, exchangeType, true, false,false, null);
        
        channel.queueDeclare(queueName, true, false, false, null);

        channel.queueBind(queueName, exchangeName, routeKey);
        
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        
        channel.basicConsume(queueName, true, queueingConsumer);

        while (true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();

            byte[] body = delivery.getBody();
            String msg = new String(body);
            System.out.printf(msg);
        }
        
    }
    
}
