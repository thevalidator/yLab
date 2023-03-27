/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson04.eventsourcing.api.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Send {

    private final static String QUEUE_NAME = "durable_queue1";//"hello";
    private static final String EXCHANGE_NAME = "logs2";

    public static void sendMessage(ConnectionFactory factory, String message) {
        try (Connection connection = factory.newConnection(); 
                Channel channel = connection.createChannel()) {

//            boolean durable = true;
//            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
//            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

////            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
////            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            
//////            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//////            String severity = "black";
//////            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes("UTF-8"));
            
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            String routingKey = "fast.yellow.lamborgini";//"fast.red.ferrari";
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            
            System.out.println(" [x] Sent '" + message + "'");

        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
