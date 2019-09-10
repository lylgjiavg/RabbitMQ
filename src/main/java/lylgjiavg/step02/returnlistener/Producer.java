package lylgjiavg.step02.returnlistener;

import com.rabbitmq.client.*;
import java.io.IOException;

/**
 * @Classname Producer
 * @Description Return消息机制：生产者
 * @Date 2019/9/8 10:50
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
        
        String exchangeName = "test_Return_Exchange";
        String routingKey = "unReturn.save";
        String msg = "Hello World send Return Message";
        
        // 添加一个Return监听
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, 
                                     AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("----------handle return-----------");
                System.out.println("replyCode:" + replyCode);
                System.out.println("replyText:" + replyText);
                System.out.println("exchange:" + exchange);
                System.out.println("routingKey:" + routingKey);
                System.out.println("properties:" + properties);
                System.out.println("body:" + new String(body));
            }
        });
        
        // 发送消息:第三个参数mandatory:true,默认为false
        channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
        
    }
    
}
