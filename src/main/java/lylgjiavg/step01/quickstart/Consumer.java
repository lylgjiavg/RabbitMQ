package lylgjiavg.step01.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @Classname Consumer
 * @Description RabbitMQ消费者
 * @Date 2019/9/2 11:02
 * @Created by Jiavg
 */
public class Consumer {

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
        
        // 4.声明(创建)一个队列
        String queueName = "test001";
        // queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,Map<String, Object> arguments)
        // param queue the name of the queue
        //  @param durable true if we are declaring a durable queue (the queue will survive a server restart)
        //  @param exclusive true if we are declaring an exclusive queue (restricted to this connection)
        //  @param autoDelete true if we are declaring an autodelete queue (server will delete it when no longer in use)
        //  @param arguments other properties (construction arguments) for the queue
        channel.queueDeclare(queueName, true, false,false,null);
        
        // 5.创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        
        // 6.设置Channel
        channel.basicConsume(queueName, true, queueingConsumer);
        
        // 7.获取消息
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            
            byte[] body = delivery.getBody();
            String msg = new String(body);
            System.out.println("消费者:" + msg);
        }
    }
}
