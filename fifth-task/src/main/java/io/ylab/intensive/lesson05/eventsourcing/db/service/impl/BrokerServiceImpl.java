/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.db.service.impl;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import io.ylab.intensive.lesson05.eventsourcing.db.service.BrokerService;
import io.ylab.intensive.lesson05.eventsourcing.db.service.IncomingMessageService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class BrokerServiceImpl implements BrokerService, DeliverCallback {
    
    private final Connection brokerConnection;
    private final IncomingMessageService messageService;
    @Value("${exchange.name}")
    public String EXCHANGE_NAME;
    @Value("${queue.name}")
    public String QUEUE_NAME;

    @Autowired
    public BrokerServiceImpl(ConnectionFactory connectionFactory,
            IncomingMessageService messageService) throws IOException, TimeoutException {
        brokerConnection = connectionFactory.newConnection();
        this.messageService = messageService;
    }

    @Override
    public void start() throws IOException {
        //try {
            Channel channel = brokerConnection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);
            String bindingKey = "db.person.*";

            Map<String, Object> arguments = new HashMap<>();
            arguments.put("x-max-length", 10_000);              //max queue length
            //arguments.put("x-queue-mode", "lazy");

            channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindingKey);
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);
            channel.basicConsume(QUEUE_NAME, true, this, tag -> {
            });
            Logger.getLogger(BrokerServiceImpl.class.getName()).log(Level.INFO, " [*] App started, waiting for messages...");
        //} catch (IOException e) {
//            if (channel != null && channel.isOpen()) {
//                try {
//                    channel.close();
//                } catch (IOException | TimeoutException e1) {
//                    Logger.getLogger(BrokerServiceImpl.class.getName()).log(Level.SEVERE, e1.getMessage());
//                }
//            }
//            if (brokerConnection != null && brokerConnection.isOpen()) {
//                try {
//                    brokerConnection.close();
//                } catch (IOException e2) {
//                    Logger.getLogger(BrokerServiceImpl.class.getName()).log(Level.SEVERE, e2.getMessage());
//                }
//            }
//            db.closeConnection();
//            throw new BrokerFailException(e.getMessage());
//        }
    }

    @Override
    public void handle(String consumerTag, Delivery message) throws IOException {
        String incomingMessage = new String(message.getBody(), StandardCharsets.UTF_8);
        messageService.handleMessage(incomingMessage);
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
