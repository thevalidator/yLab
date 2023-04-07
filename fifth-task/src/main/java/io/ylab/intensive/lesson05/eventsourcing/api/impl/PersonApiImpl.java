/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.api.impl;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.api.PersonApi;
import io.ylab.intensive.lesson05.eventsourcing.api.service.DbReadService;
import io.ylab.intensive.lesson05.eventsourcing.api.service.SendMessageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonApiImpl implements PersonApi {
    
    private final SendMessageService messageService;
    private final DbReadService dbService;

    @Autowired
    public PersonApiImpl(SendMessageService messageService, DbReadService dbService) {
        this.messageService = messageService;
        this.dbService = dbService;
    }

    @Override
    public void deletePerson(Long personId) {
        messageService.deletePerson(new Person(personId, null, null, null));
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        messageService.savePerson(new Person(personId, firstName, lastName, middleName));
    }

    @Override
    public Person findPerson(Long personId) {
        return dbService.findPerson(personId);
    }

    @Override
    public List<Person> findAll() {
        return dbService.findAll();
    }

}
