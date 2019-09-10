package lylgjiavg.step02.confirmlistener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @Classname Producer
 * @Description Confirm确认机制：生产者
 * @Date 2019/9/7 10:42
 * @Created by Jiavg
 */
public class Producer {

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        
        // 指定消息投递模式:消息的确认模式
        channel.confirmSelect();
        
        String exchangeName = "test_Confirm_Exchange";
        String routingKey = "confirm.save";
        String msg = "Hello World send Confirm Message";
        
        // 发送消息
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
        
        // 添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.printf("-------Ack-------");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.printf("------No Ack-----");
            }
        });
        
    }
    
}
