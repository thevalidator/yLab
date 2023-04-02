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
        List<String> columns = dbService.getTableColumns(tableName);
        return buildSelectQuery(tableName, columns);
    }

    @Override
    public List<String> getTables() throws SQLException {
        return dbService.getAllTables();
    }

    private String buildSelectQuery(String tableName, List<String> columns) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        if (columns.isEmpty()) {
            sb.append("*");
        } else {
            for (String column: columns) {
                sb.append(column).append(", ");
            }
            sb.setLength(sb.length() - 2);
        }
        sb.append(" FROM ").append(tableName).append(";");

        return sb.toString();
    }

}
