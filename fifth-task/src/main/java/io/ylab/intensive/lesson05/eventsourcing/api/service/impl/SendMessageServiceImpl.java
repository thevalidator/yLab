/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.api.service.impl;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.api.service.SendMessageService;
import org.springframework.stereotype.Service;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    @Override
    public void deletePerson(Person p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void savePerson(Person p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
