/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.thirdtask.datedmap;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class DatedMapDemo {
    
    private static final DatedMap dmap = new DatedMapImpl();
    
    public static void main(String[] args) throws InterruptedException {
        
        dmap.put("key1", "value1");
        TimeUnit.SECONDS.sleep(1);
        
        dmap.put("key2", "value2");
        print("key2");
        TimeUnit.SECONDS.sleep(2);
        
        dmap.put("key2", "value22");
        TimeUnit.SECONDS.sleep(1);
        
        dmap.put("key3", "value3");
        TimeUnit.SECONDS.sleep(1);
        
        dmap.put("key4", null);
        TimeUnit.SECONDS.sleep(1);
        
        dmap.put("key5", "value5");
        TimeUnit.SECONDS.sleep(1);
        
        Set<String> keys = dmap.keySet();
        for (String key : keys) {
            print(key);
        }
        
        String key = "newKey";
        print(key);
        TimeUnit.SECONDS.sleep(1);
        
        dmap.put("key2", null);
        print("key2");
        
        System.out.println(">>> " + dmap.get("rew"));
        
    }
    
    public static void print(String key) {
        System.out.printf("KEY: %7s    VALUE: %7s    DATE: %s\n", key, dmap.get(key), dmap.getKeyLastInsertionDate(key));
    }
}
