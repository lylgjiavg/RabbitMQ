package lylgjiavg.step02.customer;

import com.rabbitmq.client.*;
import java.io.IOException;

/**
 * @Classname MyConsumer
 * @Description 自定义消费端
 * @Date 2019/9/9 16:03
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
        // super.handleDelivery(consumerTag, envelope, properties, body);
        System.out.println("consumerTag:" + consumerTag + "--此条消息已处理");
        System.out.println("\t消息内容:" + new String(body));

        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}