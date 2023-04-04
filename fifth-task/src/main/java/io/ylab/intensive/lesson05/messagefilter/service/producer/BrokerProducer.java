/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.messagefilter.service.producer;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import io.ylab.intensive.lesson05.messagefilter.service.ConnectionManager;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BrokerProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrokerProducer.class);
    private final BasicProperties messageProperties;
    private final Channel channel;
    private final String queueName;

    @Autowired
    public BrokerProducer(ConnectionManager manager,
            @Value("${queue.out.name}") String queueName,
            BasicProperties messageProperties) throws IOException {
        
        this.queueName = queueName;
        channel = manager.getInputChannel();
        initChannel(channel, queueName);
        this.messageProperties = messageProperties;
        LOGGER.info(" [*] Producer started...");

    }

    public void sendMessage(String message) throws IOException {
        channel.basicPublish("", queueName,
                messageProperties,
                message.getBytes("UTF-8"));
    }

    private void initChannel(Channel channel, String queueName) throws IOException {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-length", 10_000);
        channel.queueDeclare(queueName, true, false, false, arguments);
    }

}
