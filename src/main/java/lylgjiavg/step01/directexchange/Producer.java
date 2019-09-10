package lylgjiavg.step01.directexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Classname Producer
 * @Description RabbitMQ的Direct模式:生产者
 * @Date 2019/9/3 13:27
 * @Created by Jiavg
 */
public class Producer {

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
        
        // 4.声明交换机名称及路由键
        String exchangeName = "test_Direct_Exchange";
        String routeKey = "test.direct";
        
        // 5.发送消息
        String msg = "Hello World RabbitMQ 4 Direct Exchange message...";
        channel.basicPublish(exchangeName, routeKey, null, msg.getBytes());
        
        // 6.关闭资源
        channel.close();
        connection.close();

    }
    
}
