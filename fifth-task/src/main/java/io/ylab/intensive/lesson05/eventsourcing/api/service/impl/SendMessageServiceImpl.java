/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.api.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import static io.ylab.intensive.lesson05.eventsourcing.entity.message.ActionType.DELETE;
import static io.ylab.intensive.lesson05.eventsourcing.entity.message.ActionType.SAVE;
import io.ylab.intensive.lesson05.eventsourcing.entity.message.Message;
import io.ylab.intensive.lesson05.eventsourcing.api.service.SendMessageService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendMessageServiceImpl implements SendMessageService {
    
    private final ObjectMapper objectMapper;
    private final Connection connection;
    private final Channel channel;
    private final BasicProperties props;
    
    @Value("${exchange.name}")
    public String EXCHANGE_NAME;// = "person";
    @Value("${route.key.save}")
    public String SAVE_ROUTING_KEY;// = "db.person.save";
    @Value("${route.key.delete}")
    public String DELETE_ROUTING_KEY;// = "db.person.delete";

    @Autowired
    public SendMessageServiceImpl(ConnectionFactory connectionFactory, 
            BasicProperties props, ObjectMapper objectMapper) throws IOException, TimeoutException {
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);
        this.props = props;
        this.objectMapper = objectMapper;
    }

    @Override
    public void deletePerson(Person p) {
        try {
            String message = objectMapper.writeValueAsString(new Message(p, DELETE));
            channel.basicPublish(EXCHANGE_NAME, DELETE_ROUTING_KEY, props, message.getBytes("UTF-8"));
        } catch (JsonProcessingException | UnsupportedEncodingException ex) {
            Logger.getLogger(SendMessageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SendMessageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void savePerson(Person p) {
        try {
            String message = objectMapper.writeValueAsString(new Message(p, SAVE));
            channel.basicPublish(EXCHANGE_NAME, SAVE_ROUTING_KEY, props, message.getBytes("UTF-8"));
        } catch (JsonProcessingException | UnsupportedEncodingException ex) {
            Logger.getLogger(SendMessageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SendMessageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
