/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson04.eventsourcing.db.service;

import io.ylab.intensive.lesson04.eventsourcing.Person;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface DbHandler {

    void savePerson(Person p);

    void deletePerson(Person p);

    public void closeConnection();

}
