package util;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ExceptionHandler;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQSender {
    private static final String QUEUE_NAME = System.getenv("RABBITMQ_QUEUE") != null ? System.getenv("RABBITMQ_QUEUE") : "my_queue";
    private static final String EXCHANGE_NAME = System.getenv("RABBITMQ_EXCHANGE") != null ? System.getenv("RABBITMQ_EXCHANGE") : "my_exchange";
    private static final String ROUTING_KEY = System.getenv("RABBITMQ_ROUTING_KEY") != null ? System.getenv("RABBITMQ_ROUTING_KEY") : "my_routing_key";

    private Connection connection;
    private Channel channel;

    public RabbitMQSender() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(System.getenv("RABBITMQ_HOST") != null ? System.getenv("RABBITMQ_HOST") : "localhost");
        factory.setUsername(System.getenv("RABBITMQ_USER") != null ? System.getenv("RABBITMQ_USER") : "guest");
        factory.setPassword(System.getenv("RABBITMQ_PASSWORD") != null ? System.getenv("RABBITMQ_PASSWORD") : "guest");

        // Optional: Enable SSL/TLS
        // factory.useSslProtocol();

        connection = factory.newConnection();
        channel = connection.createChannel();

        // Declare exchange and queue
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
    }

    public Channel getChannel() throws IOException {
        if (channel == null || !channel.isOpen()) {
            throw new IOException("Channel is not available");
        }
        return channel;
    }

    public void close() {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
        } catch (IOException | TimeoutException e) {
            // Log or handle the exception
            e.printStackTrace();
        }
    }
}
