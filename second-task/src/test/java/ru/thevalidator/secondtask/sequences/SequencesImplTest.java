/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.secondtask.sequences;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.AfterEach;
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
public class SequencesImplTest {

    private static final ByteArrayOutputStream content = new ByteArrayOutputStream();
    private static final PrintStream console = System.out;

    private final SequencesImpl instance;

    public SequencesImplTest() {
        instance = new SequencesImpl();
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

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("Sequence A for N = 0")
    public void testAForZero() throws UnsupportedEncodingException {
        int n = 0;
        instance.a(n);
        assertEquals("", content.toString("UTF-8"));
    }

    @Test
    @DisplayName("Sequence A for N = 1")
    public void testAForOne() throws UnsupportedEncodingException {
        int n = 1;
        instance.a(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "2";
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Sequence A for N=6")
    public void testAForSix() throws UnsupportedEncodingException {
        int n = 6;
        instance.a(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "2\r\n4\r\n6\r\n8\r\n10\r\n12";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Sequence A when N is negative")
    public void testAForNegative() throws UnsupportedEncodingException {
        int n = -1;
        instance.a(n);
        String actual = content.toString("UTF-8").trim();
        assertEquals("", actual);
    }

    @Test
    @DisplayName("Sequence B for N = 0")
    public void testBForZero() throws UnsupportedEncodingException {
        int n = 0;
        instance.b(n);
        assertEquals("", content.toString("UTF-8"));
    }

    @Test
    @DisplayName("Sequence B for N = 1")
    public void testBForOne() throws UnsupportedEncodingException {
        int n = 1;
        instance.b(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1";
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Sequence B for N=6")
    public void testBForSix() throws UnsupportedEncodingException {
        int n = 6;
        instance.b(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1\r\n3\r\n5\r\n7\r\n9\r\n11";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Sequence B when N is negative")
    public void testBForNegative() throws UnsupportedEncodingException {
        int n = -1;
        instance.b(n);
        String actual = content.toString("UTF-8").trim();
        assertEquals("", actual);
    }
    
    @Test
    @DisplayName("Sequence C for N = 0")
    public void testCForZero() throws UnsupportedEncodingException {
        int n = 0;
        instance.c(n);
        assertEquals("", content.toString("UTF-8"));
    }

    @Test
    @DisplayName("Sequence C for N = 1")
    public void testCForOne() throws UnsupportedEncodingException {
        int n = 1;
        instance.c(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1";
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Sequence C for N=6")
    public void testCForSix() throws UnsupportedEncodingException {
        int n = 6;
        instance.c(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1\r\n4\r\n9\r\n16\r\n25\r\n36";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Sequence C when N is negative")
    public void testCForNegative() throws UnsupportedEncodingException {
        int n = -1;
        instance.c(n);
        String actual = content.toString("UTF-8").trim();
        assertEquals("", actual);
    }

    @Test
    @DisplayName("Sequence D for N = 0")
    public void testDForZero() throws UnsupportedEncodingException {
        int n = 0;
        instance.d(n);
        assertEquals("", content.toString("UTF-8"));
    }

    @Test
    @DisplayName("Sequence D for N = 1")
    public void testDForOne() throws UnsupportedEncodingException {
        int n = 1;
        instance.d(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1";
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Sequence D for N=6")
    public void testDForSix() throws UnsupportedEncodingException {
        int n = 6;
        instance.d(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1\r\n8\r\n27\r\n64\r\n125\r\n216";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Sequence D when N is negative")
    public void testDForNegative() throws UnsupportedEncodingException {
        int n = -1;
        instance.d(n);
        String actual = content.toString("UTF-8").trim();
        assertEquals("", actual);
    }

    @Test
    @DisplayName("Sequence E for N = 0")
    public void testEForZero() throws UnsupportedEncodingException {
        int n = 0;
        instance.e(n);
        assertEquals("", content.toString("UTF-8"));
    }

    @Test
    @DisplayName("Sequence E for N = 1")
    public void testEForOne() throws UnsupportedEncodingException {
        int n = 1;
        instance.e(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1";
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Sequence E for N=6")
    public void testEForSix() throws UnsupportedEncodingException {
        int n = 6;
        instance.e(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1\r\n-1\r\n1\r\n-1\r\n1\r\n-1";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Sequence E when N is negative")
    public void testEForNegative() throws UnsupportedEncodingException {
        int n = -1;
        instance.e(n);
        String actual = content.toString("UTF-8").trim();
        assertEquals("", actual);
    }

    @Test
    @DisplayName("Sequence F for N = 0")
    public void testFForZero() throws UnsupportedEncodingException {
        int n = 0;
        instance.f(n);
        assertEquals("", content.toString("UTF-8"));
    }

    @Test
    @DisplayName("Sequence F for N = 1")
    public void testFForOne() throws UnsupportedEncodingException {
        int n = 1;
        instance.f(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1";
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Sequence F for N=6")
    public void testFForSix() throws UnsupportedEncodingException {
        int n = 6;
        instance.f(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1\r\n-2\r\n3\r\n-4\r\n5\r\n-6";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Sequence F when N is negative")
    public void testFForNegative() throws UnsupportedEncodingException {
        int n = -1;
        instance.f(n);
        String actual = content.toString("UTF-8").trim();
        assertEquals("", actual);
    }

    @Test
    @DisplayName("Sequence G for N = 0")
    public void testGForZero() throws UnsupportedEncodingException {
        int n = 0;
        instance.g(n);
        assertEquals("", content.toString("UTF-8"));
    }

    @Test
    @DisplayName("Sequence G for N = 1")
    public void testGForOne() throws UnsupportedEncodingException {
        int n = 1;
        instance.g(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1";
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Sequence G for N=6")
    public void testGForSix() throws UnsupportedEncodingException {
        int n = 6;
        instance.g(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1\r\n-4\r\n9\r\n-16\r\n25\r\n-36";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Sequence G when N is negative")
    public void testGForNegative() throws UnsupportedEncodingException {
        int n = -1;
        instance.g(n);
        String actual = content.toString("UTF-8").trim();
        assertEquals("", actual);
    }
    
    @Test
    @DisplayName("Sequence H for N = 0")
    public void testHForZero() throws UnsupportedEncodingException {
        int n = 0;
        instance.h(n);
        assertEquals("", content.toString("UTF-8"));
    }

    @Test
    @DisplayName("Sequence H for N = 1")
    public void testHForOne() throws UnsupportedEncodingException {
        int n = 1;
        instance.h(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1";
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Sequence H for N=6")
    public void testHForSix() throws UnsupportedEncodingException {
        int n = 6;
        instance.h(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1\r\n0\r\n2\r\n0\r\n3\r\n0";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Sequence H when N is negative")
    public void testHForNegative() throws UnsupportedEncodingException {
        int n = -1;
        instance.h(n);
        String actual = content.toString("UTF-8").trim();
        assertEquals("", actual);
    }

    @Test
    @DisplayName("Sequence I for N = 0")
    public void testIForZero() throws UnsupportedEncodingException {
        int n = 0;
        instance.i(n);
        assertEquals("", content.toString("UTF-8"));
    }

    @Test
    @DisplayName("Sequence I for N = 1")
    public void testIForOne() throws UnsupportedEncodingException {
        int n = 1;
        instance.i(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1";
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Sequence I for N=6")
    public void testIForSix() throws UnsupportedEncodingException {
        int n = 6;
        instance.i(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1\r\n2\r\n6\r\n24\r\n120\r\n720";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Sequence I when N is negative")
    public void testIForNegative() throws UnsupportedEncodingException {
        int n = -1;
        instance.i(n);
        String actual = content.toString("UTF-8").trim();
        assertEquals("", actual);
    }

    @Test
    @DisplayName("Sequence J for N = 0")
    public void testJForZero() throws UnsupportedEncodingException {
        int n = 0;
        instance.j(n);
        assertEquals("", content.toString("UTF-8"));
    }

    @Test
    @DisplayName("Sequence J for N = 1")
    public void testJForOne() throws UnsupportedEncodingException {
        int n = 1;
        instance.j(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1";
        assertEquals(expected, actual);
    }
    
    @Test
    @DisplayName("Sequence J for N=6")
    public void testJForSix() throws UnsupportedEncodingException {
        int n = 6;
        instance.j(n);
        String actual = content.toString("UTF-8").trim();
        String expected = "1\r\n1\r\n2\r\n3\r\n5\r\n8";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Sequence J when N is negative")
    public void testJForNegative() throws UnsupportedEncodingException {
        int n = -1;
        instance.j(n);
        String actual = content.toString("UTF-8").trim();
        assertEquals("", actual);
    }

}
