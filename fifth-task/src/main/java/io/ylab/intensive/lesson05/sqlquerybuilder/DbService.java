/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.sqlquerybuilder;

import java.util.List;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface DbService {

    List<String> getAllTables();

    List<String> getTableColumns(String tableName);

}
