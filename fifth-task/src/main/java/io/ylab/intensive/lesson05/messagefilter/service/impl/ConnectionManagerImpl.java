/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.messagefilter.service.impl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.messagefilter.service.ConnectionManager;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConnectionManagerImpl implements ConnectionManager {

    private final java.sql.Connection dbConnection;
    private final com.rabbitmq.client.Connection brokerConnection;
    private final Channel inputChannel;
    private final Channel outputChannel;

    @Autowired
    public ConnectionManagerImpl(DataSource dataSource, 
            ConnectionFactory connectionFactory,
            @Value("${queue.in.name}") String inputQueue,
            @Value("${queue.out.name}") String outputQueue) throws SQLException, IOException, TimeoutException {
        dbConnection = dataSource.getConnection();
        brokerConnection = connectionFactory.newConnection();
        inputChannel = brokerConnection.createChannel();
        outputChannel = brokerConnection.createChannel();
        
    }

    @Override
    public java.sql.Connection getDbConnection() {
        return dbConnection;
    }
    
    @Override
    public com.rabbitmq.client.Connection getBrokerConnection() {
        return brokerConnection;
    }
    
    @Override
    public Channel getInputChannel() {
        return inputChannel;
    }

    @Override
    public Channel getOutputChannel() {
        return outputChannel;
    }

    @PreDestroy
    public void preDestroy() {
        try {
            dbConnection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
