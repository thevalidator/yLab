/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.passwordvalidator;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public enum ErrorType {
    
    LOGIN_TOO_LONG ("Логин слишком длинный"),
    LOGIN_IS_NULL("Логин null"),
    LOGIN_HAS_INVALID_SYMBOL("Логин содержит недопустимые символы"),
    PASSWORD_HAS_INVALID_SYMBOL("Пароль содержит недопустимые символы"),
    PASSWORD_TOO_LONG("Пароль слишком длинный"),
    PASSWORD_IS_NULL("Пароль null"),
    PASSWORDS_DOESNT_MATCH("Пароль и подтверждение не совпадают");
    
    private final String message;

    private ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
