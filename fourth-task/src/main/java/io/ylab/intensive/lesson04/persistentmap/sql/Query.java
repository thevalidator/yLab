/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson04.persistentmap.sql;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Query {
    
    public static final String CONTAINS = ""
            + "SELECT \"key\" "
            + "FROM persistent_map "
            + "WHERE map_name=? "
            + "AND \"key\"=?;";
    
    public static final String GET_KEYS = ""
            + "SELECT \"key\" "
            + "FROM persistent_map "
            + "WHERE map_name=?;";
    
    public static final String GET = ""
            + "SELECT value "
            + "FROM persistent_map "
            + "WHERE map_name=? "
            + "AND \"key\"=?;";
    
    public static final String REMOVE = ""
            + "DELETE FROM persistent_map "
            + "WHERE map_name=? "
            + "AND \"key\"=?;";
    
    public static final String PUT = ""
            + "INSERT INTO persistent_map (map_name, \"key\", value) "
            + "VALUES(?, ?, ?);";
    
    public static final String CLEAR = "TRUNCATE persistent_map;";    

}
