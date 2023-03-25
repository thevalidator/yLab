/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class PersistentMapImplTest {
    
    PersistentMapImpl instance;
    static DataSource dataSource;
    static String createTestTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
    
    @BeforeAll
    public static void setUpClass() throws SQLException {
        dataSource = DbUtil.buildDataSource();
    }
    
    @BeforeEach
    public void setUp() throws SQLException {
        DbUtil.applyDdl(createTestTable, dataSource);
    }

    @Test
    public void testContainsKeyIfTableIsEmpty() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = "test key";
        boolean expResult = false;
        boolean result = instance.containsKey(key);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsKeyIfTableHasKey() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = "test key";
        String value = "test value";
        instance.put(key, value);
        boolean expResult = true;
        boolean result = instance.containsKey(key);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testContainsKeyIfKeyIsNull() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = null;
        String value = "test value";
        instance.put(key, value);
        boolean expResult = true;
        boolean result = instance.containsKey(key);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetKeysEmptyTable() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        int expSize = 0;
        List<String> keys = instance.getKeys();
        for (String key: keys) {
            System.out.println("> " +  key);
        }
        assertEquals(expSize, keys.size());
    }

    @Test
    public void testGetKeysTableHasTwoKeys() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = "test key";
        String value = "test value";
        String key2 = "test2 key";
        String value2 = "test2 value";
        instance.put(key, value);
        instance.put(key2, value2);
        int expSize = 2;
        List<String> keys = instance.getKeys();
        assertEquals(expSize, keys.size());
        assertEquals(key, keys.get(0));
        assertEquals(key2, keys.get(1));
    }

    @Test
    public void testGetEmptyTable() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = "test key";
        
        String expResult = null;
        String result = instance.get(key);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetTableHasValues() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = "test key";
        String value = "test value";
        String key2 = "test2 key";
        String value2 = "test2 value";
        instance.put(key, value);
        instance.put(key2, value2);
        
        String expResult = value;
        String expResult2 = value2;
        String result = instance.get(key);
        String result2 = instance.get(key2);
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
    }

    @Test
    public void testRemove() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = "test key";
        String value = "test value";
        String key2 = "test2 key";
        String value2 = "test2 value";
        instance.put(key, value);
        instance.put(key2, value2);
        
        assertEquals(true, instance.containsKey(key));
        instance.remove(key);
        assertEquals(false, instance.containsKey(key));
        assertEquals(1, instance.getKeys().size());
    }

    @Test
    public void testPut() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = "test key";
        String value = "test value";
        String key2 = "test2 key";
        String value2 = "test2 value";
        
        assertEquals(0, instance.getKeys().size());
        instance.put(key, value);
        assertEquals(1, instance.getKeys().size());
        assertEquals(value, instance.get(key));
        instance.put(key2, value2);
        assertEquals(2, instance.getKeys().size());
        assertEquals(value2, instance.get(key2));
    }
    
    @Test
    public void testPutKeyIsNull() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = null;
        String value = "test value";
        
        assertEquals(0, instance.getKeys().size());
        instance.put(key, value);
        assertEquals(1, instance.getKeys().size());
        assertEquals(value, instance.get(key));
    }
    
    @Test
    public void testPutValueIsNull() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = "test key";
        String value = null;
        
        assertEquals(0, instance.getKeys().size());
        instance.put(key, value);
        assertEquals(0, instance.getKeys().size());
        assertEquals(value, instance.get(key));
        assertEquals(false, instance.containsKey(key));
    }

    @Test
    public void testClear() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = "test key";
        String value = "test value";
        String key2 = "test2 key";
        String value2 = "test2 value";
        instance.put(key, value);
        instance.put(key2, value2);
        
        assertEquals(2, instance.getKeys().size());
        instance.clear();
        assertEquals(0, instance.getKeys().size());
    }
    
    @Test
    public void testWithTwoMaps() throws Exception {
        String name = "test Name";
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = "test key";
        String value = "test value";
        String key2 = "test2 key";
        String value2 = "test2 value";
        
        String name2 = "test Name2";
        PersistentMapImpl instance2 = new PersistentMapImpl(dataSource);
        instance2.init(name2);
        String value3 = "test value3";
        
        instance.put(key, value);
        instance.put(key2, value2);
        instance2.put(key, value3);
        
        assertEquals(2, instance.getKeys().size());
        assertEquals(1, instance2.getKeys().size());
        
        assertEquals(value, instance.get(key));
        assertEquals(value3, instance2.get(key));
    }
    
    @Test
    public void testMapNameIsNull() throws Exception {
        String name = null;
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = "test key";
        String value = "test value";
        String key2 = "test2 key";
        String value2 = "test2 value";
        instance.put(key, value);
        instance.put(key2, value2);
        
        assertEquals(2, instance.getKeys().size());
        assertEquals(value, instance.get(key));
        assertEquals(value2, instance.get(key2));
    }
    
    @Test
    public void testMapNameIsNullAndKeyIsNull() throws Exception {
        String name = null;
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = null;
        String value = "test value";
        String key2 = "test2 key";
        String value2 = "test2 value";
        instance.put(key, value);
        instance.put(key2, value2);
        
        assertEquals(2, instance.getKeys().size());
        assertEquals(value, instance.get(key));
        assertEquals(value2, instance.get(key2));
    }
    
    @Test
    public void testMapNameIsNullAndKeyIsNullAndValueIsNull() throws Exception {
        String name = null;
        instance = new PersistentMapImpl(dataSource);
        instance.init(name);
        String key = null;
        String value = null;
        String key2 = "test2 key";
        String value2 = "test2 value";
        instance.put(key, value);
        instance.put(key2, value2);
        
        assertEquals(1, instance.getKeys().size());
        assertEquals(false, instance.containsKey(key));
        assertEquals(value, instance.get(key));
        assertEquals(value2, instance.get(key2));
    }
    
}
