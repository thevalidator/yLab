/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.db.service;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface IncomingMessageService {
    
    void handleMessage(String incomingMessage);

}
