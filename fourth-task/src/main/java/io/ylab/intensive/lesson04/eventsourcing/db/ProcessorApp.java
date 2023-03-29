/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson04.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.communication.message.ActionType;
import io.ylab.intensive.lesson04.eventsourcing.communication.message.Message;
import io.ylab.intensive.lesson04.eventsourcing.db.service.DbHandler;
import io.ylab.intensive.lesson04.eventsourcing.db.service.impl.DbHandlerImpl;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class ProcessorApp {

    private static final String RPC_QUEUE_NAME = "rpc_queue";

    public static void main(String[] argv) throws Exception {
        Object ob = new Object();
        ObjectMapper mapper = new ObjectMapper();
        java.sql.Connection dbConn = null;
        DataSource dataSource = initDb();

        ConnectionFactory factory = initMQ();
        factory.setHost("localhost");

        Connection connection = null;
        try {

            dbConn = dataSource.getConnection();
            dbConn.setAutoCommit(true);
            DbHandler dbHandler = new DbHandlerImpl(dbConn);

            connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                        byte[] body) throws IOException {
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                            .correlationId(properties.getCorrelationId()).build();

                    String response = "";

                    try {
                        String incomingMessage = new String(body, "UTF-8");
                        Integer dbRes = null;
                        try {
                            Message message = mapper.readValue(incomingMessage, Message.class);
                            if (message.getAction().equals(ActionType.SAVE)) {
                                dbHandler.savePerson(message.getPerson());
                            } else {
                                dbRes = dbHandler.deletePerson(message.getPerson());
                            }
                        } catch (JsonProcessingException ex) {
                            Logger.getLogger(DbApp.class.getName()).log(Level.SEVERE, ex.getMessage());
                        }

                        response = dbRes == null ? response : String.valueOf(dbRes);
                    } catch (RuntimeException e) {
                        System.out.println(" [.] " + e.toString());
                    } finally {
                        // Return to the processing result queue
                        channel.basicPublish("", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
                        // Confirm the message, the following parameters have been received multiple: whether to batch. true: all messages smaller than envelope.getDeliveryTag() will be confirmed at one time.
                        channel.basicAck(envelope.getDeliveryTag(), false);
                        // RabbitMq consumer worker thread notifies the RPC
                        // server owner thread
                        synchronized (ob) {
                            ob.notify();
                        }
                    }
                }
            };
            //Cancel automatic confirmation
            boolean autoAck = false;
            channel.basicConsume(RPC_QUEUE_NAME, autoAck, consumer);
            // Wait and be prepared to consume the message from RPC client.
            while (true) {
                synchronized (consumer) {
                    try {
                        consumer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                try {
                connection.close();
            } catch (IOException _ignore) {
            }
        }
    }

    private static DataSource initDb() throws SQLException {
        DataSource dataSource = DbUtil.buildDataSource();
        return dataSource;
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}
