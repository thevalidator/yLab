/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.datedmap;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class DatedMapImpl implements DatedMap {

    private final HashMap<String, Object[]> map;

    public DatedMapImpl() {
        map = new HashMap<>();
    }

    @Override
    public void put(String key, String value) {
        Object[] v = null;
        if (value != null) {
            Date date = new Date(System.currentTimeMillis());
            v = new Object[]{value, date};
        }
        map.put(key, v);
    }

    @Override
    public String get(String key) {
        return map.containsKey(key) ? getValue(map.get(key)) : null;
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(String key) {
        map.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        return map.containsKey(key) ? getDate(map.get(key)) : null;
    }

    private String getValue(Object[] array) {
        if (array == null) {
            return null;
        } else {
            return (String) array[0];
        }
    }

    private Date getDate(Object[] array) {
        if (array == null) {
            return null;
        } else {
            return (Date) array[1];
        }
    }

}
