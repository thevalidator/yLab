/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.sqlquerybuilder.impl;

import io.ylab.intensive.lesson05.sqlquerybuilder.DbService;
import io.ylab.intensive.lesson05.sqlquerybuilder.SQLQueryBuilder;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    
    private final DbService dbService;

    @Autowired
    public SQLQueryBuilderImpl(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getTables() throws SQLException {
        return dbService.getAllTables();
    }

}
