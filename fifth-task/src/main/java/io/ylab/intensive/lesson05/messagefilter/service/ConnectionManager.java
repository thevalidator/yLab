/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.messagefilter.service;

import java.sql.Connection;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface ConnectionManager {
    
    Connection getDbConnection();

}
