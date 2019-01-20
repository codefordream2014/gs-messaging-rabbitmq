package hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv {

    private final static String QUEUE_NAME = "spring-boot";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.3");
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.basicQos(1);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            long deliveryTag = delivery.getEnvelope().getDeliveryTag();
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            if(!message.contains("hey")) {
                channel.basicReject(deliveryTag, true);
            } else {
                channel.basicAck(deliveryTag, false);
            }
           /* try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
            }*/

        };
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });

    }
}
