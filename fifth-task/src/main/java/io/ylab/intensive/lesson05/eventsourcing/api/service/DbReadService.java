/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.api.service;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import java.util.List;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface DbReadService {
    
    Person findPerson(Long personId);
    
    List<Person> findAll();

}
