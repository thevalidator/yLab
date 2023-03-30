/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson04.eventsourcing.db.exception;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class BrokerFailException extends RuntimeException {

    public BrokerFailException(String message) {
        super(message);
    }
    
}
