package lylgjiavg.step01.directexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @Classname Consumer
 * @Description RabbitMQ的Direct模式:消费者
 * @Date 2019/9/3 13:37
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

        String exchangeName = "test_Direct_Exchange";
        String exchangeType = "direct";
        String queueName = "test_Direct_Queue";
        String routeKey = "test.direct";
        
        // 4.声明一个交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true, false, false, null);
        
        // 5.声明一个队列
        channel.queueDeclare(queueName, true, false, false, null);
        
        // 6. 建立一个绑定关系
        channel.queueBind(queueName, exchangeName, routeKey);

        // 7.创建一个消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        
        // 8.设置信道参数
        channel.basicConsume(queueName, true, consumer);
        
        // 9.读取消息
        while (true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            byte[] body = delivery.getBody();
            String msg = new String(body);
            System.out.println(msg);
        }
        
        
        
    }
    
}
