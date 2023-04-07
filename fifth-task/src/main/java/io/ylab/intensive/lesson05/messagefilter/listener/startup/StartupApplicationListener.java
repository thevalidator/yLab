/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.messagefilter.listener.startup;

import io.ylab.intensive.lesson05.messagefilter.service.ConnectionManager;
import io.ylab.intensive.lesson05.messagefilter.sql.Query;
import java.io.File;
import java.io.FileInputStream;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
@Component
@PropertySource("classpath:filter.properties")
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StartupApplicationListener.class);
    private final Connection connection;
    private final String dictionaryName;

    @Autowired
    public StartupApplicationListener(ConnectionManager manager, 
            @Value("${dictionary.name}") String dictionaryName) {
        connection = manager.getDbConnection();
        this.dictionaryName = dictionaryName;
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
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private boolean isExistsTable() {
        boolean isExists = false;
        try {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet rs = databaseMetaData.getTables(null, null, Query.TABLE_NAME, new String[]{"TABLE"});
            isExists = rs.next();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return isExists;
    }

    private void fillTable() {
        int batchSize = 5_000;
        try (InputStream is = new FileInputStream(new File(dictionaryName)); 
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

        } catch (SQLException | IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void clearTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(Query.CLEAR);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

}
