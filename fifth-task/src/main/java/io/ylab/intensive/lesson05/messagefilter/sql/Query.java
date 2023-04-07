/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.messagefilter.sql;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Query {

    public static final String TABLE_NAME = "bad_word";

    public static final String DDL = ""
            + "CREATE TABLE bad_word ( "
            + "word varchar(50) NOT NULL, "
            + "CONSTRAINT unq_tbl_word UNIQUE (word));";

    public static final String IS_EXISTS = ""
            + "SELECT exists "
            + "("
            + "SELECT 1 "
            + "FROM bad_word "
            + "WHERE word = LOWER(?) "
            + "LIMIT 1"
            + ");";

    public static final String INSERT = ""
            + "INSERT INTO bad_word (word) "
            + "VALUES(LOWER(?));";

    public static final String CLEAR = "TRUNCATE bad_word;";
    
}
