/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.secondtask.snilsvalidator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class SnilsValidatorImplTest {
    
    private static final SnilsValidatorImpl instance = new SnilsValidatorImpl();

    @Test
    public void testValidateWrongSnils() {
        String snils = "1527-sdas61414";
        boolean expResult = false;
        boolean result = instance.validate(snils);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateNotValidSnils() {
        String snils = "15278361414";
        boolean expResult = false;
        boolean result = instance.validate(snils);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateValidSnils() {
        String snils = "16675209900";
        boolean expResult = true;
        boolean result = instance.validate(snils);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetChecksum() {
        int sum = 181;
        int expResult = 80;
        int result = instance.getChecksum(sum);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetsum() {
        int expResult = 1*9 + 5*8 + 2*7 + 7*6 + 8*5 + 3*4 + 6*3 + 1*2 + 4;
        int result = instance.getSum("15278361414");
        assertEquals(expResult, result);
    }
    
}
