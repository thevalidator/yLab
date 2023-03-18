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
    LOGIN_TOO_SHORT("Логин слишком короткий"),
    LOGIN_HAS_INVALID_SYMBOL("Логин содержит недопустимые символы"),
    PASSWORD_HAS_INVALID_SYMBOL("Пароль содержит недопустимые символы"),
    PASSWORD_TOO_LONG("Пароль слишком длинный"),
    PASSWORD_TOO_SHORT("Пароль слишком короткий"),
    PASSWORDS_DOESNT_MATCH("Пароль и подтверждение не совпадают");
    
    private final String message;

    private ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
