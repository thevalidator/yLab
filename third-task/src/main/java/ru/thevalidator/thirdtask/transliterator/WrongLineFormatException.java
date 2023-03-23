/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.thirdtask.transliterator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class WrongLineFormatException extends Exception {

    public WrongLineFormatException(String line) {
        super("Wrong format in line: " + line);
    }

}
