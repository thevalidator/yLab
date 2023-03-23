/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.thirdtask.passwordvalidator.exception;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class WrongPasswordException extends Exception {

    public WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }

}
