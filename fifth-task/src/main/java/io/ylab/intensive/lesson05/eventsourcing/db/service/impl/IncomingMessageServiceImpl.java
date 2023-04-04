/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.db.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.intensive.lesson05.eventsourcing.db.service.IncomingMessageService;
import io.ylab.intensive.lesson05.eventsourcing.entity.message.ActionType;
import io.ylab.intensive.lesson05.eventsourcing.entity.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.ylab.intensive.lesson05.eventsourcing.db.service.DbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class IncomingMessageServiceImpl implements IncomingMessageService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingMessageServiceImpl.class);
    private final ObjectMapper objectMapper;
    private final DbService dbService;

    @Autowired
    public IncomingMessageServiceImpl(ObjectMapper objectMapper, DbService dbWriteService) {
        this.objectMapper = objectMapper;
        this.dbService = dbWriteService;
    }

    @Override
    public void handleMessage(String incomingMessage) {
        try {
            Message message = objectMapper.readValue(incomingMessage, Message.class);
            if (message.getAction().equals(ActionType.SAVE)) {
                dbService.savePerson(message.getPerson());
            } else {
                dbService.deletePerson(message.getPerson());
            }
        } catch (JsonProcessingException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

}
