/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.datedmap;

import java.util.Date;
import java.util.Set;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface DatedMap {

    void put(String key, String value);

    String get(String key);

    boolean containsKey(String key);

    void remove(String key);

    Set<String> keySet();

    Date getKeyLastInsertionDate(String key);
}
