/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.api.service.impl;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.api.service.DbReadService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DbReadServiceImpl implements DbReadService {

    @Override
    public Person findPerson(Long personId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Person> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
