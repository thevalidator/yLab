/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.messagefilter.startup;

import io.ylab.intensive.lesson05.messagefilter.service.ConnectionManager;
import io.ylab.intensive.lesson05.messagefilter.sql.Query;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    
    private final Connection connection;

    @Autowired
    public StartupApplicationListener(ConnectionManager manager) {
        connection = manager.getDbConnection();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!isExistsTable()) {
            createTable();
        }
        clearTable();
        fillTable();
    }

    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(Query.DDL);
        } catch (SQLException ex) {
            Logger.getLogger(StartupApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean isExistsTable() {
        boolean isExists = false;
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet rs = databaseMetaData.getTables(null, null, Query.TABLE_NAME, new String[]{"TABLE"});
            isExists = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(StartupApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isExists;
    }

    private void fillTable() {
        int batchSize = 5_000;
        try (InputStream is = new FileInputStream(new File("words.dict")); 
                LineNumberReader lr = new LineNumberReader(new InputStreamReader(is, "UTF-8")); 
                PreparedStatement ps = connection.prepareStatement(Query.INSERT);) {

            String word;
            while ((word = lr.readLine()) != null && !word.isBlank()) {
                ps.setString(1, word);
                ps.addBatch();
                if (lr.getLineNumber() == batchSize) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();

        } catch (SQLException ex) {
            Logger.getLogger(StartupApplicationListener.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StartupApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StartupApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(Query.CLEAR);
        } catch (SQLException ex) {
            Logger.getLogger(StartupApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
