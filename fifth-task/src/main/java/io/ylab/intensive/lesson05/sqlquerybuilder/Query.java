/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.sqlquerybuilder;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Query {

    public static final String GET_ALL_TABLES_NAMES = ""
            + "SELECT tablename "
            + "FROM pg_catalog.pg_tables "
            + "WHERE schemaname != 'pg_catalog' "
            + "AND schemaname != 'information_schema';";
    
}
