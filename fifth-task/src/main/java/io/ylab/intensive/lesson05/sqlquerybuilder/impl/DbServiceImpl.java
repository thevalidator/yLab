/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.sqlquerybuilder.impl;

import io.ylab.intensive.lesson05.sqlquerybuilder.DbService;
import static io.ylab.intensive.lesson05.sqlquerybuilder.MetaData.COLUMN_NAME;
import io.ylab.intensive.lesson05.sqlquerybuilder.Query;
import jakarta.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbServiceImpl implements DbService {

    private final Connection connection;

    @Autowired
    public DbServiceImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    @PreDestroy
    public void preDestroy() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<String> getAllTables() {
        List<String> tables = new ArrayList<>();
        try (Statement st = connection.createStatement();) {
            ResultSet rs = st.executeQuery(Query.GET_ALL_TABLES_NAMES);
            while (rs.next()) {
                tables.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbServiceImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        return tables;
    }

    @Override
    public List<String> getTableColumns(String tableName) {
        List<String> columns = null;
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            if (exists(databaseMetaData, tableName)) {
                columns = new ArrayList<>();
                try (ResultSet rs = databaseMetaData.getColumns(null, null, tableName, null)) {
                    while (rs.next()) {
                        String columnName = rs.getString(COLUMN_NAME.name());
                        columns.add(columnName);
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DbServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return columns;
    }

    private boolean exists(DatabaseMetaData databaseMetaData, String tableName) throws SQLException {
        ResultSet resultSet = databaseMetaData.getTables(null, null, tableName, new String[]{"TABLE"});

        return resultSet.next();
    }

}
