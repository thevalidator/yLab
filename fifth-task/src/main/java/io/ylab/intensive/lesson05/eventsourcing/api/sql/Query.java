/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.eventsourcing.api.sql;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Query {

    public static String GET = "SELECT person_id, first_name, last_name, middle_name "
                                + "FROM person "
                                + "WHERE person.person_id=?;";
    
    public static String GET_ALL = "SELECT person_id, first_name, last_name, middle_name "
                                + "FROM person;";
    
}
