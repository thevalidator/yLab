/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson04.eventsourcing.db.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Reciever {

    //private final static String QUEUE_NAME = "durable_queue1";//"hello";
    private static final String EXCHANGE_NAME = "logs2";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

//        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

////        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
////        String queueName = channel.queueDeclare().getQueue();
////        channel.queueBind(queueName, EXCHANGE_NAME, "");
    
//////        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
//////        String queueName = channel.queueDeclare().getQueue();       
//////        String severities = "white,black";
//////        for (String severity : severities.split(",")) {
//////            channel.queueBind(queueName, EXCHANGE_NAME, severity);
//////        }
        
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();
        String bindingKey = "*.red.*";
        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() 
                    + "':'" + message + "'");

            try {
                doWork(message);
            } catch (InterruptedException ex) {
                Logger.getLogger(Reciever.class.getName()).log(Level.SEVERE, ex.getMessage());
            } finally {
                System.out.println(" [x] Done");
            }

        };
        boolean isHandled = true;
        //channel.basicConsume(QUEUE_NAME, isHandled, deliverCallback, consumerTag -> {
        channel.basicConsume(queueName, isHandled, deliverCallback, consumerTag -> {
        });
    }

    private static void doWork(String task) throws InterruptedException {
//        int counter = 0;
//        for (char ch: task.toCharArray()) {
//            if (ch == '.') {
//                counter++;
//            }
//        }
//        TimeUnit.SECONDS.sleep(counter);
        System.out.println("working " + Thread.currentThread().getName());
    }
}