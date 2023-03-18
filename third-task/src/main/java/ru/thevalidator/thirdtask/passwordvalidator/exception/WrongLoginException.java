/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.passwordvalidator.exception;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class WrongLoginException extends Exception {

    public WrongLoginException() {
    }

    public WrongLoginException(String message) {
        super(message);
    }
    
}
