/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.messagefilter.service.impl;

import io.ylab.intensive.lesson05.messagefilter.service.ConnectionManager;
import io.ylab.intensive.lesson05.messagefilter.service.DbService;
import io.ylab.intensive.lesson05.messagefilter.sql.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbServiceImpl implements DbService {
    
    private final Connection connection;

    @Autowired
    public DbServiceImpl(ConnectionManager manager) {
        this.connection = manager.getDbConnection();
    }

    @Override
    public boolean isWordExists(String word) {
        boolean isExists = false;
        try (PreparedStatement ps = connection.prepareStatement(Query.IS_EXISTS);) {
            ps.setString(1, word);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isExists = rs.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbServiceImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        
        return isExists;
    }

}
