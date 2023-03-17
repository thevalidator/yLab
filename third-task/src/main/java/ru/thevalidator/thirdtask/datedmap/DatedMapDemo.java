/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.thirdtask.datedmap;

import java.util.Set;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class DatedMapDemo {
    
    private static final DatedMap dmap = new DatedMapImpl();
    
    public static void main(String[] args) {
        
        dmap.put("key1", "value1");
        dmap.put("key2", "value2");
        dmap.put("key2", "value22");
        dmap.put("key3", "value3");
        dmap.put("key4", null);
        dmap.put("key5", "value5");
        
        Set<String> keys = dmap.keySet();
        System.out.println("KEYSET: " + keys);
        
        for (String key : keys) {
            print(key);
        }
        String key = "newKey";
        print(key);
        
    }
    
    public static void print(String key) {
        System.out.printf("KEY: %7s    VALUE: %7s    DATE: %s\n", key, dmap.get(key), dmap.getKeyLastInsertionDate(key));
    }
}
