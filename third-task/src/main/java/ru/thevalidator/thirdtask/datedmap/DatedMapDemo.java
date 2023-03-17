/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.thirdtask.datedmap;

import java.util.Date;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class DatedMapDemo {
    public static void main(String[] args) {
        
        Date date = new Date(System.currentTimeMillis());
        System.out.println("> " + date);
        
        DatedMap dmap = new DatedMapImpl();
        dmap.put("asd2", "111");
        dmap.put("asd2", "222");
        dmap.put("asd3", "111");
        dmap.put("asd4", null);
        System.out.println(dmap.getKeyLastInsertionDate("asd"));
        dmap.remove("22");
        System.out.println(dmap.keySet());
        System.out.println(dmap.getKeyLastInsertionDate("asd4"));
        
    }
}
