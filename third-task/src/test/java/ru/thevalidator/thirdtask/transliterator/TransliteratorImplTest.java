/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.transliterator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class TransliteratorImplTest {
    
    public static final TransliteratorImpl instance = new TransliteratorImpl();

    @Test
    public void testTransliterateLatinAlphabet() {
        String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String expResult = source;
        String result = instance.transliterate(source);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testTransliterateCyrillicAlphabet() {
        String source = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЬЪЭЮЯабвгдеёжзийклмнопрстуфхцчшщыьъэюя";
        String expResult = "ABVGDEEZHZIIKLMNOPRSTUFKHTSCHSHSHCHYIEEIUIAабвгдеёжзийклмнопрстуфхцчшщыьъэюя";
        String result = instance.transliterate(source);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testTransliterateMixOFLatinAndCyrillicLetters() {
        String source = "ABCDabcdАБВГабвг";
        String expResult = "ABCDabcdABVGабвг";
        String result = instance.transliterate(source);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testTransliterateNullString() {
        String source = null;
        String expResult = null;
        String result = instance.transliterate(source);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testTransliterateEmptyString() {
        String source = "";
        String expResult = "";
        String result = instance.transliterate(source);
        assertEquals(expResult, result);
    }
    
}
