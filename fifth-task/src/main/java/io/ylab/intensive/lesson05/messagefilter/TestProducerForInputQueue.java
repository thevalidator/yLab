/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class TestProducerForInputQueue {

    public static void main(String[] args) {
        
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {

            Map<String, Object> arguments = new HashMap<>();
            arguments.put("x-max-length", 10_000);
            channel.queueDeclare("input", true, false, false, arguments);

            Scanner sc = new Scanner(System.in);
            System.out.println("AVAILABLE COMMANDS:\n"
                    + "1)            EXIT: exit\n"
                    + "\nEnter message:");

            String input;
            while (!(input = sc.nextLine()).equals("exit")) {
                try {
                    channel.basicPublish("", "input",
                            MessageProperties.PERSISTENT_TEXT_PLAIN,
                            input.getBytes("UTF-8"));
                } catch (IOException e) {

                }
            }
            System.out.println("GOODBYE!");

        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(TestProducerForInputQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
