/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.messagefilter.service;

import com.rabbitmq.client.Channel;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface ConnectionManager {
    
    java.sql.Connection getDbConnection();
    
    com.rabbitmq.client.Connection getBrokerConnection();
    
    Channel getInputChannel();
    
    Channel getOutputChannel();

}
