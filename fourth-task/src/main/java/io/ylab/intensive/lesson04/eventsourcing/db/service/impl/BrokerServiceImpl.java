/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson04.eventsourcing.db.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.communication.message.ActionType;
import io.ylab.intensive.lesson04.eventsourcing.communication.message.Message;
import io.ylab.intensive.lesson04.eventsourcing.communication.routing.Data;
import static io.ylab.intensive.lesson04.eventsourcing.communication.routing.Data.QUEUE_NAME;
import io.ylab.intensive.lesson04.eventsourcing.db.DbApp;
import io.ylab.intensive.lesson04.eventsourcing.db.exception.BrokerFailException;
import io.ylab.intensive.lesson04.eventsourcing.db.service.BrokerService;
import io.ylab.intensive.lesson04.eventsourcing.db.service.DbHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrokerServiceImpl implements BrokerService, DeliverCallback {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final DbHandler dbHandler;
    private Connection brokerConn;
    private Channel channel;

    public BrokerServiceImpl(DbHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void handleMessage(String input) {
        try {
            Message message = mapper.readValue(input, Message.class);
            if (message.getAction().equals(ActionType.SAVE)) {
                dbHandler.savePerson(message.getPerson());
            } else {
                dbHandler.deletePerson(message.getPerson());
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(DbApp.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void handle(String consumerTag, Delivery message) throws IOException {
        String incomingMessage = new String(message.getBody(), StandardCharsets.UTF_8);
        handleMessage(incomingMessage);
    }

    @Override
    public void start() {
        try {
            brokerConn = RabbitMQUtil.buildConnectionFactory().newConnection();
            channel = brokerConn.createChannel();
            channel.exchangeDeclare(Data.EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);
            String bindingKey = "db.person.*";

            Map<String, Object> arguments = new HashMap<>();
            arguments.put("x-max-length", 10_000);              //max queue length
            //arguments.put("x-queue-mode", "lazy");

            channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);
            channel.queueBind(QUEUE_NAME, Data.EXCHANGE_NAME, bindingKey);
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);
            channel.basicConsume(QUEUE_NAME, true, this, tag -> {
            });
            Logger.getLogger(BrokerServiceImpl.class.getName()).log(Level.INFO, " [*] App started, waiting for messages...");
        } catch (IOException | TimeoutException e) {
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException | TimeoutException e1) {
                    Logger.getLogger(BrokerServiceImpl.class.getName()).log(Level.SEVERE, e1.getMessage());
                }
            }
            if (brokerConn != null && brokerConn.isOpen()) {
                try {
                    brokerConn.close();
                } catch (IOException e2) {
                    Logger.getLogger(BrokerServiceImpl.class.getName()).log(Level.SEVERE, e2.getMessage());
                }
            }
            dbHandler.closeConnection();
            throw new BrokerFailException(e.getMessage());
        }
    }

}
