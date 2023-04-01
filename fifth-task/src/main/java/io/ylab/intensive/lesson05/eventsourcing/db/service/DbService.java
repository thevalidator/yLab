/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.db.service;

import io.ylab.intensive.lesson05.eventsourcing.Person;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface DbService {
    
    void savePerson(Person p);
    
    void deletePerson(Person p);

}
