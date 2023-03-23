/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.passwordvalidator;

import ru.thevalidator.thirdtask.passwordvalidator.exception.WrongLoginException;
import ru.thevalidator.thirdtask.passwordvalidator.exception.WrongPasswordException;
import static ru.thevalidator.thirdtask.passwordvalidator.ErrorType.*;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class PasswordValidator {

    private static final int MAX_LENGTH = 20;
    private static final String PATTERN = "[A-Za-z0-9_]+";

    public static boolean validate(String login, String password, String confirmPassword) {
        boolean result = false;
        try {

            if (login == null) {
                throw new WrongLoginException(LOGIN_IS_NULL.getMessage());
            } else if (password == null) {
                throw new WrongPasswordException(PASSWORD_IS_NULL.getMessage());
            } else if (login.length() >= MAX_LENGTH) {
                throw new WrongLoginException(LOGIN_TOO_LONG.getMessage());
            } else if (password.length() >= MAX_LENGTH) {
                throw new WrongPasswordException(PASSWORD_TOO_LONG.getMessage());
            } else if (!login.isEmpty() && !login.matches(PATTERN)) {
                throw new WrongLoginException(LOGIN_HAS_INVALID_SYMBOL.getMessage());
            } else if (!password.isEmpty() && !password.matches(PATTERN)) {
                throw new WrongPasswordException(PASSWORD_HAS_INVALID_SYMBOL.getMessage());
            } else if (confirmPassword == null || !password.equals(confirmPassword)) {
                throw new WrongPasswordException(PASSWORDS_DOESNT_MATCH.getMessage());
            }
            result = true;

        } catch (WrongLoginException | WrongPasswordException e) {
            System.err.println("ERROR: " + e.getMessage());
        } finally {
            return result;
        }
    }

}
