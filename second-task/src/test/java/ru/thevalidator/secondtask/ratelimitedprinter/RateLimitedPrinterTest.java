/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.secondtask.ratelimitedprinter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class RateLimitedPrinterTest {

    private static final ByteArrayOutputStream content = new ByteArrayOutputStream();
    private static final PrintStream console = System.out;

    public RateLimitedPrinterTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        System.setOut(new PrintStream(content));
    }

    @AfterAll
    public static void tearDownClass() {
        System.setOut(console);
    }
    
    @BeforeEach
    public void setUp() {
        content.reset();
    }

    @Test
    @DisplayName("Test print interval equals 1 second")
    public void testPrintWithIntervalOneSecond() throws UnsupportedEncodingException {
        int delay = 1_000;
        String message = "test";
        RateLimitedPrinter instance = new RateLimitedPrinter(delay);
       
        instance.print(message);     //  should print
        long tsmp = System.currentTimeMillis();
        while ((System.currentTimeMillis() - tsmp) <= delay) {
            instance.print(message);    //  should NOT print
        }
        instance.print(message);    //  should print
        
        String actual = content.toString("UTF-8").trim();
        String expected = "test\r\ntest";
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Test print with no delay")
    public void testPrintNoInterval() throws UnsupportedEncodingException {
        int delay = 0;
        String message = "test";
        RateLimitedPrinter instance = new RateLimitedPrinter(delay);
        
        for (int i = 0; i < 5; i++) {
            instance.print(message);
        }
        
        String actual = content.toString("UTF-8").trim();
        String expected = "test\r\ntest\r\ntest\r\ntest\r\ntest";
        assertEquals(expected, actual);
    }

}
