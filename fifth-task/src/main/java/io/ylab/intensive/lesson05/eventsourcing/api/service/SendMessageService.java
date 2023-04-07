/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.api.service;

import io.ylab.intensive.lesson05.eventsourcing.Person;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface SendMessageService {
    
    void deletePerson(Person p);
    
    void savePerson(Person p);

}
