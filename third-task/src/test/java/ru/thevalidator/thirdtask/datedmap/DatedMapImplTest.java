/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.datedmap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class DatedMapImplTest {

    @Test
    public void testPut() {
        String key = "testKey";
        String value = "testValue";
        DatedMapImpl instance = new DatedMapImpl();
        instance.put(key, value);
        
        assertEquals(true, instance.containsKey(key));
        assertEquals(value, instance.get(key));
        assertEquals(1, instance.keySet().size());
        assertTrue(instance.getKeyLastInsertionDate(key) != null);
    }
    
    @Test
    public void testPutKeyEqualsNull() {
        String key = null;
        String value = "testValue";
        DatedMapImpl instance = new DatedMapImpl();
        instance.put(key, value);
        
        assertEquals(true, instance.containsKey(key));
        assertEquals(value, instance.get(key));
        assertEquals(1, instance.keySet().size());
        assertTrue(instance.getKeyLastInsertionDate(key) != null);
    }
    
    @Test
    public void testPutKeyIsBlank() {
        String key = "";
        String value = "testValue";
        DatedMapImpl instance = new DatedMapImpl();
        instance.put(key, value);
        
        assertEquals(true, instance.containsKey(key));
        assertEquals(value, instance.get(key));
        assertEquals(1, instance.keySet().size());
        assertTrue(instance.getKeyLastInsertionDate(key) != null);
    }
    
    @Test
    public void testPutValueEqualsNull() {
        String key = "testKey";
        String value = null;
        DatedMapImpl instance = new DatedMapImpl();
        instance.put(key, value);
        
        assertEquals(true, instance.containsKey(key));
        assertEquals(value, instance.get(key));
        assertEquals(1, instance.keySet().size());
        assertTrue(instance.getKeyLastInsertionDate(key) == null);
    }

    @Test
    public void testGetFromEmptyMap() {
        String key = "testKey";
        DatedMapImpl instance = new DatedMapImpl();
        String expResult = null;
        String result = instance.get(key);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testContainsKeyEmptyMap() {
        String key = "testKey";
        DatedMapImpl instance = new DatedMapImpl();
        boolean expResult = false;
        boolean result = instance.containsKey(key);
        
        assertEquals(expResult, result);
    }

    @Test
    public void testRemove() {
        String key = "testKey";
        String value = "testValue";
        String key2 = "testKey2";
        String value2 = "testValue2";
        DatedMapImpl instance = new DatedMapImpl();
        instance.put(key, value);
        instance.put(key2, value2);
        
        assertEquals(true, instance.containsKey(key));
        assertEquals(2, instance.keySet().size());
        
        instance.remove(key);
        
        assertEquals(false, instance.containsKey(key));
        assertEquals(1, instance.keySet().size());
        assertTrue(instance.get(key) == null);
        assertTrue(instance.getKeyLastInsertionDate(key) == null);
    }

    @Test
    public void testKeySet() {
        String key = "testKey";
        String value = "testValue";
        DatedMapImpl instance = new DatedMapImpl();
        
        assertEquals(0, instance.keySet().size());
        instance.put(key, value);
        assertEquals(1, instance.keySet().size());
        instance.remove(key);
        assertEquals(0, instance.keySet().size());        
    }

    @Test
    public void testGetKeyLastInsertionDate() {
        String key = "testKey";
        String value = "testValue";
        DatedMapImpl instance = new DatedMapImpl();
        
        assertTrue(instance.getKeyLastInsertionDate(key) == null);
        instance.put(key, null);
        assertTrue(instance.getKeyLastInsertionDate(key) == null);
        instance.put(key, value);
        assertTrue(instance.getKeyLastInsertionDate(key) != null);
    }
    
}
