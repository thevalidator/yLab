/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson04.eventsourcing.db.sql;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Query {
    
    public static String SAVE = "INSERT INTO person (person_id, first_name, last_name, middle_name) \n" +
                                "VALUES(?, ?, ?, ?) \n" +
                                "ON CONFLICT (person_id) DO \n" +
                                "UPDATE SET first_name=?, last_name=?, middle_name=? where person.person_id=?;";
    
    public static String DELETE = "DELETE FROM person WHERE person_id=?;";

}
