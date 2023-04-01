/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.db.service;

import java.io.IOException;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface BrokerService {
    
    void start() throws IOException;
    
    void stop();

}
