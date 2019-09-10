package lylgjiavg.step01.fanoutexchange;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Classname Producer
 * @Description RabbitMQ的Fanout模式:生产者
 * @Date 2019/9/3 22:14
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

        String exchangeName = "test_Fanout_Exchange";
        String routeKey = "";
        
        String msg = "Hello World Fanout Exchange";
        
        while (true){
            channel.basicPublish(exchangeName, routeKey, null, msg.getBytes());
        }
        
    }
    
}
