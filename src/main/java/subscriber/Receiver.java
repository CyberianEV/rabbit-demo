package subscriber;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Receiver {
    private final String EXCHANGER_NAME = "blog_article_exchanger";
    private final ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private Map<String, String> queues;
    private static Receiver instance;


    private Receiver() throws Exception {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        queues = new HashMap<>();
        channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.DIRECT);
    }

    public static Receiver getInstance() throws Exception {
        if (instance == null) {
            instance = new Receiver();
        }
        return instance;
    }

    public void subscribeOnTopic(String topicName) throws IOException {
        if (queues.containsKey(topicName)) {
            System.out.println("You are already subscribed to topic: " + topicName);
            return;
        }
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGER_NAME, topicName);
        queues.put(topicName, queueName);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.printf("New article on '%s' topic received: '%s'\n", topicName, message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
        System.out.println("You are now subscribed on topic: " + topicName);
    }

    public void unsubscribeFromTopic(String topicName) throws IOException {
        if (!queues.containsKey(topicName)) {
            System.out.println("You are not yet subscribed to topic: " + topicName);
            return;
        }
        String queueName = queues.get(topicName);
        channel.queueDelete(queueName);
        System.out.println("You unsubscribed from topic: " + topicName);
    }

    public void close() {
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
