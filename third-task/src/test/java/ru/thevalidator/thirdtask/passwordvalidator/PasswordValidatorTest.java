/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.passwordvalidator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static ru.thevalidator.thirdtask.passwordvalidator.ErrorType.*;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class PasswordValidatorTest {
    
    private static final ByteArrayOutputStream content = new ByteArrayOutputStream();
    private static final PrintStream console = System.out;
    private static final String VALID_LOGIN = "Gabcd123_";
    private static final String VALID_PASSWORD = "Yqwerty_2000";
    
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
    public void testValidateShouldBeTrue() throws UnsupportedEncodingException {
        String login = VALID_LOGIN;
        String password = VALID_PASSWORD;
        String confirmPassword = password;
        boolean expResult = true;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals("", content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateLoginHasInvalidSymbols() throws UnsupportedEncodingException {
        String login = "123qweASD+";
        String password = VALID_PASSWORD;
        String confirmPassword = password;
        String error = "ERROR: " + LOGIN_HAS_INVALID_SYMBOL.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidatePasswordHasInvalidSymbols() throws UnsupportedEncodingException {
        String login = VALID_LOGIN;
        String password = "%sdad";
        String confirmPassword = password;
        String error = "ERROR: " + PASSWORD_HAS_INVALID_SYMBOL.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }

    @Test
    public void testValidateLoginIsNull() throws UnsupportedEncodingException {
        String login = null;
        String password = VALID_PASSWORD;
        String confirmPassword = password;
        String error = "ERROR: " + LOGIN_TOO_SHORT.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidatePasswordIsNull() throws UnsupportedEncodingException {
        String login = VALID_LOGIN;
        String password = null;
        String confirmPassword = "";
        String error = "ERROR: " + PASSWORD_TOO_SHORT.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateConfirmPasswordIsNull() throws UnsupportedEncodingException {
        String login = VALID_LOGIN;
        String password = VALID_PASSWORD;
        String confirmPassword = null;
        String error = "ERROR: " + PASSWORDS_DOESNT_MATCH.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateConfirmPasswordIsNOTEqualsPassword() throws UnsupportedEncodingException {
        String login = VALID_LOGIN;
        String password = VALID_PASSWORD;
        String confirmPassword = "345435435345";
        String error = "ERROR: " + PASSWORDS_DOESNT_MATCH.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateLoginLengthMoreThanTwenty() throws UnsupportedEncodingException {
        String login = "1234567890987654321qwerty";
        String password = VALID_PASSWORD;
        String confirmPassword = password;
        String error = "ERROR: " + LOGIN_TOO_LONG.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidatePasswordLengthMoreThanTwenty() throws UnsupportedEncodingException {
        String login = VALID_LOGIN;
        String password = "1234567890987654321qwertyu";
        String confirmPassword = VALID_PASSWORD;
        String error = "ERROR: " + PASSWORD_TOO_LONG.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateLoginLengthEqualsTwenty() throws UnsupportedEncodingException {
        String login = "12345678901234567890";
        String password = VALID_PASSWORD;
        String confirmPassword = password;
        String error = "ERROR: " + LOGIN_TOO_LONG.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidatePasswordLengthEqualsTwenty() throws UnsupportedEncodingException {
        String login = VALID_LOGIN;
        String password = "12345678901234567890";
        String confirmPassword = password;
        String error = "ERROR: " + PASSWORD_TOO_LONG.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateLoginLengthEqualsThree() throws UnsupportedEncodingException {
        String login = "123";
        String password = VALID_PASSWORD;
        String confirmPassword = password;
        String error = "ERROR: " + LOGIN_TOO_SHORT.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidatePasswordLengthEqualsThree() throws UnsupportedEncodingException {
        String login = VALID_LOGIN;
        String password = "123";
        String confirmPassword = password;
        String error = "ERROR: " + PASSWORD_TOO_SHORT.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateLoginLengthLessThanThree() throws UnsupportedEncodingException {
        String login = "1";
        String password = VALID_PASSWORD;
        String confirmPassword = password;
        String error = "ERROR: " + LOGIN_TOO_SHORT.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidatePasswordLengthLessThanThree() throws UnsupportedEncodingException {
        String login = VALID_LOGIN;
        String password = "22";
        String confirmPassword = password;
        String error = "ERROR: " + PASSWORD_TOO_SHORT.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateLoginIsEmpty() throws UnsupportedEncodingException {
        String login = "";
        String password = VALID_PASSWORD;
        String confirmPassword = password;
        String error = "ERROR: " + LOGIN_TOO_SHORT.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidatePasswordIsEmpty() throws UnsupportedEncodingException {
        String login = VALID_LOGIN;
        String password = "";
        String confirmPassword = "";
        String error = "ERROR: " + PASSWORD_TOO_SHORT.getMessage();
        boolean expResult = false;
        boolean result = PasswordValidator.validate(login, password, confirmPassword);
        
        assertEquals(error, content.toString("UTF-8").trim());
        assertEquals(expResult, result);
    }
    
}
