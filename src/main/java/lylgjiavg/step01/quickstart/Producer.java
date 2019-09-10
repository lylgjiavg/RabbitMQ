package lylgjiavg.step01.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Classname Producer
 * @Description RabbitMQ生产者
 * @Date 2019/9/2 10:54
 * @Created by Jiavg
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        
        // 1.创建一个ConnectionFactory,并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
 
        // 2.通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        // 3.通过connection创建一个Channel
        Channel channel = connection.createChannel();
        
        for (int i = 0; i < 5; i++){
            String message = "Hello RabbitMQ!";
            // 4.通过Channel发送数据
            // basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)
            channel.basicPublish("", "test001", null, message.getBytes());
        }

        // 5.关闭相关连接
        channel.close();
        connection.close();;
        
    }
    
}
