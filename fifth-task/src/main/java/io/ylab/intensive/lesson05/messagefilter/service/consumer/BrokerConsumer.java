/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.messagefilter.service.consumer;

import com.rabbitmq.client.Channel;
import io.ylab.intensive.lesson05.messagefilter.service.ConnectionManager;
import io.ylab.intensive.lesson05.messagefilter.service.SwearingSubstitutionService;
import io.ylab.intensive.lesson05.messagefilter.service.producer.BrokerProducer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BrokerConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrokerConsumer.class);
    
    private final SwearingSubstitutionService substitutionService;
    private final BrokerProducer producer;

    @Autowired
    public BrokerConsumer(ConnectionManager manager,
            @Value("${queue.in.name}") String inputQueue,
            SwearingSubstitutionService substitutionService,
            BrokerProducer producer) throws IOException {
        
        this.producer = producer;
        this.substitutionService = substitutionService;
        Channel channel = manager.getInputChannel();
        initChannel(channel, inputQueue);
        LOGGER.info(" [*] Consumer started, waiting for messages...");
        
    }

    private void initChannel(Channel channel, String inputQueue) throws IOException {

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-length", 10_000);              //max queue length
        channel.queueDeclare(inputQueue, true, false, false, arguments);
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        channel.basicConsume(inputQueue, true, (consumerTag, message) -> {
            
            String inputMsg = new String(message.getBody(), StandardCharsets.UTF_8);
            String afterCensor = substitutionService.censor(inputMsg);
            producer.sendMessage(afterCensor);
            
        }, tag -> {
        });

    }

}
