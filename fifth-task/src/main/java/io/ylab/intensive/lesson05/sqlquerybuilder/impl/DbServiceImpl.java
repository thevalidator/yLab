/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.sqlquerybuilder.impl;

import io.ylab.intensive.lesson05.sqlquerybuilder.DbService;
import io.ylab.intensive.lesson05.sqlquerybuilder.Query;
import jakarta.annotation.PreDestroy;
import java.sql.Connection;
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
    
}
