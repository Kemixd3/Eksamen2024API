package ProgrammeringsEksamenAPI.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.rabbitmq.client.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@RestController
public class CommandController {


    @PostMapping("/send-command")
    public ResponseEntity<String> sendCommand(@RequestBody String command) {
        try {
            // Create a temporary connection and channels
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setUsername("guest");
            factory.setPassword("guest");

            try (Connection connection = factory.newConnection()) {
                try (Channel commandChannel = connection.createChannel()) {
                    // Declare the command exchange and queue
                    commandChannel.exchangeDeclare("command_exchange", "direct", true);
                    commandChannel.queueDeclare("command_queue", true, false, false, null);
                    commandChannel.queueBind("command_queue", "command_exchange", "command_key");

                    // Declare a reply queue and set up a consumer
                    String replyQueue = commandChannel.queueDeclare().getQueue();
                    final String correlationId = java.util.UUID.randomUUID().toString();

                    AMQP.BasicProperties props = new AMQP.BasicProperties
                            .Builder()
                            .correlationId(correlationId)
                            .replyTo(replyQueue)
                            .build();

                    commandChannel.basicPublish("command_exchange", "command_key", props, (correlationId + ":" + command).getBytes());

                    // Set up a consumer to listen on the reply queue
                    final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
                    commandChannel.basicConsume(replyQueue, true, (consumerTag, delivery) -> {
                        if (delivery.getProperties().getCorrelationId().equals(correlationId)) {
                            response.offer(new String(delivery.getBody(), "UTF-8"));
                        }
                    }, consumerTag -> {});

                    // Wait and return the response from the command
                    String result = response.take();
                    return ResponseEntity.ok(result);
                }
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending command: " + e.getMessage());
        }
    }

    }



