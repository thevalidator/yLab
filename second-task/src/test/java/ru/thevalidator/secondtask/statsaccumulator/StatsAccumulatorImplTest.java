/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.secondtask.statsaccumulator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class StatsAccumulatorImplTest {
    
    static StatsAccumulatorImpl instance;
    
    public StatsAccumulatorImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        instance = new StatsAccumulatorImpl();
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testIfEmpty() {
        assertAll(
                () -> assertEquals(0, instance.getCount()),
                () -> assertEquals(Integer.MAX_VALUE, instance.getMin()),
                () -> assertEquals(Integer.MIN_VALUE, instance.getMax()),
                () -> assertEquals(0., instance.getAvg())
        );
    }

    @Test
    public void testAddOneNumber() {
        int value = 1;
        instance.add(value);
        assertAll(
                () -> assertEquals(1, instance.getCount()),
                () -> assertEquals(1, instance.getMin()),
                () -> assertEquals(1, instance.getMax()),
                () -> assertEquals(1., instance.getAvg())
        );
    }
    
    @Test
    public void testAddThreeNumbers() {
        int value1 = -5;
        int value2 = 0;
        int value3 = 5;
        instance.add(value1);
        instance.add(value2);
        instance.add(value3);
        assertAll(
                () -> assertEquals(3, instance.getCount()),
                () -> assertEquals(value1, instance.getMin()),
                () -> assertEquals(value3, instance.getMax()),
                () -> assertEquals(0., instance.getAvg())
        );
    }
    
    @Test
    public void testAdd() {
        instance.add(10);
        int expResult = 1;
        int result = instance.getCount();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMin() {
        int expResult = Integer.MAX_VALUE;
        int result = instance.getMin();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMax() {
        int value = 45;
        instance.add(value);
        int result = instance.getMax();
        assertEquals(value, result);
    }

    @Test
    public void testGetCount() {
        instance.add(56);
        instance.add(56);
        instance.add(56);
        int expResult = 3;
        int result = instance.getCount();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAvg() {
        instance.add(15);
        instance.add(0);
        Double expResult = 7.5;
        Double result = instance.getAvg();
        assertEquals(expResult, result);
    }
    
}
