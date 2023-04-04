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
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class BrokerServiceImpl implements BrokerService, DeliverCallback {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BrokerServiceImpl.class);
    private final IncomingMessageService messageService;
    private final ConnectionFactory connectionFactory;
    private Connection brokerConnection;
    private Channel channel;
    @Value("${exchange.name}")
    public String EXCHANGE_NAME;
    @Value("${queue.name}")
    public String QUEUE_NAME;

    @Autowired
    public BrokerServiceImpl(ConnectionFactory connectionFactory,
            IncomingMessageService messageService) {
        this.connectionFactory = connectionFactory;
        this.messageService = messageService;
    }

    @Override
    public void start() throws IOException, TimeoutException {
            brokerConnection = connectionFactory.newConnection();
            channel = brokerConnection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);
            String bindingKey = "db.person.*";

            Map<String, Object> arguments = new HashMap<>();
            arguments.put("x-max-length", 10_000);              //max queue length

            channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindingKey);
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);
            channel.basicConsume(QUEUE_NAME, true, this, tag -> {
            });
            LOGGER.info(" [*] App started, waiting for messages...");
    }

    @Override
    public void handle(String consumerTag, Delivery message) throws IOException {
        String incomingMessage = new String(message.getBody(), StandardCharsets.UTF_8);
        messageService.handleMessage(incomingMessage);
    }

    @Override
    public void stop() {
        preDestroy();
    }
    
    @PreDestroy
    public void preDestroy() {
        try {
            channel.close();
        } catch (IOException | TimeoutException ex) {
            LOGGER.info(ex.getMessage());
        }
        try {
            brokerConnection.close();
        } catch (IOException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

}
