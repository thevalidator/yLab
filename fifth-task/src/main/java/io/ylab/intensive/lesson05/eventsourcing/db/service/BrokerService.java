/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.db.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface BrokerService {
    
    void start() throws IOException, TimeoutException;
    
    void stop();

}
