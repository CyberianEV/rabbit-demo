package blog;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {
    private final String EXCHANGER_NAME = "blog_article_exchanger";
    private final ConnectionFactory factory;

    private static Sender instance;

    private Sender() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
    }

    public static Sender getInstance() {
        if (instance == null) {
            instance = new Sender();
        }
        return instance;
    }

    public void sendArticle(BlogArticle article) throws Exception {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.DIRECT);
            channel.basicPublish(EXCHANGER_NAME, article.getTopic(), null, article.getText().getBytes());
        }
    }
}
