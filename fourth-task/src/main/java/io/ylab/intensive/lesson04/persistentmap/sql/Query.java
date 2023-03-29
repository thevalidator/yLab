/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson04.persistentmap.sql;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Query {
    
    private static final String IS_NULL = " IS NULL";
    private static final String VALUE = "=?";
    
    public static String getQuery(boolean nameIsNull, boolean keyIsNull) {
        String mapName = nameIsNull ? IS_NULL : VALUE;
        String key = keyIsNull ? IS_NULL : VALUE;
        String query = String.format("SELECT value FROM persistent_map WHERE map_name%s AND \"key\"%s;", mapName, key);
        
        return query;
    }
    
    public static String getKeysQuery(boolean nameIsNull) {
        String mapName = nameIsNull ? IS_NULL : VALUE;
        String query = String.format("SELECT \"key\" FROM persistent_map WHERE map_name%s AND value IS NOT NULL;", mapName);
        
        return query;
    }
    
    public static String removeQuery(boolean nameIsNull, boolean keyIsNull) {
        String mapName = nameIsNull ? IS_NULL : VALUE;
        String key = keyIsNull ? IS_NULL : VALUE;
        String query = String.format("DELETE FROM persistent_map WHERE map_name%s AND \"key\"%s;", mapName, key);
        
        return query;
    }
    
    public static String putQuery() {
       return "INSERT INTO persistent_map (map_name, \"key\", value) VALUES(?, ?, ?);";
    }
    
    public static String clearQuery() {
       return "DELETE FROM persistent_map WHERE map_name=?;";
    } 

}
