package lylgjiavg.step02.nautoack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @Classname MyConsumer
 * @Description 消费端ACK:自定义客户端监听
 * @Date 2019/9/8 13:15
 * @Created by Jiavg
 */
public class MyConsumer extends DefaultConsumer {
    
    private Channel channel;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     * @param channel the channel to which this consumer is attached
     */
    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }
    
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("body:" + new String(body));
        // 延时ack
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if((Integer)properties.getHeaders().get("num") == 1){
            // basicNack(long deliveryTag, boolean multiple, boolean requeue);
            channel.basicNack(envelope.getDeliveryTag(), false, true);
        }else {
            // basicAck(long deliveryTag, boolean multiple) throws IOException;
            channel.basicAck(envelope.getDeliveryTag(), false);
        }
    }
}
