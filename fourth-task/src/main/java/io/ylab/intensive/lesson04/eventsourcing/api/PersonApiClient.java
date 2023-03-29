/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class PersonApiClient {

    private final Connection connection;
    private final Channel channel;
    private final String requestQueueName = "rpc_queue";
    private String replyQueueName;

    public PersonApiClient() throws IOException, TimeoutException {
        //Establish a connection and a channel, and declare a unique'callback' queue for the callback
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();
        channel = connection.createChannel();
        //Define a temporary variable accept queue name    
        replyQueueName = channel.queueDeclare().getQueue();
    }

    //Send RPC request  
    public String call(String message) throws IOException, InterruptedException {
        //Generate a unique string as the number of the callback queue
        String corrId = UUID.randomUUID().toString();
        //Send a request message, the message uses two attributes: replyto and correlationId
        //The server returns the result according to the replyto, and the client judges whether the response is for itself according to the correlationId
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName)
                .build();

        //Publish a message, requestQueueName routing rules
        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        //Since our consumer transaction processing is performed in a separate thread, we need to suspend the main thread before the response arrives.
        //Here we created a blocking queue ArrayBlockingQueue with a capacity of 1, because we only need to wait for a response.
        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        // String basicConsume(String queue, boolean autoAck, Consumer callback)
        channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                //Check whether its correlationId is the one we are looking for
                if (properties.getCorrelationId().equals(corrId)) {
                    //If yes, respond to BlockingQueue
                    response.offer(new String(body, "UTF-8"));
                }
            }
        });

        return response.take();
    }

    public void close() throws IOException {
        connection.close();
    }
}
