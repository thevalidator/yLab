/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson04.eventsourcing.communication.routing;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Data {

    public static final String EXCHANGE_NAME = "person";
    public static final String QUEUE_NAME = "person_query";
    public static final String SAVE_ROUTING_KEY = "db.person.save";
    public static final String DELETE_ROUTING_KEY = "db.person.delete";
    
}
