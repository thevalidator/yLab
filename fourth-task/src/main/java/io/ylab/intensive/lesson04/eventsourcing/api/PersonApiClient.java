/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson04.eventsourcing.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.api.sql.Query;
import static io.ylab.intensive.lesson04.eventsourcing.communication.message.ActionType.DELETE;
import static io.ylab.intensive.lesson04.eventsourcing.communication.message.ActionType.SAVE;
import io.ylab.intensive.lesson04.eventsourcing.communication.message.Message;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class PersonApiClient implements PersonApi {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final Connection connection;
    private final java.sql.Connection dbConn;
    private final Channel channel;
    private final String requestQueueName = "rpc_queue";
    private String replyQueueName;
            

    public PersonApiClient() throws Exception {
        //Establish a connection and a channel, and declare a unique'callback' queue for the callback
        ConnectionFactory factory = initMQ();
        factory.setHost("localhost");
        dbConn = initDb().getConnection();
        dbConn.setAutoCommit(true);

        connection = factory.newConnection();
        channel = connection.createChannel();
        //Define a temporary variable accept queue name    
        replyQueueName = channel.queueDeclare().getQueue();
    }

    //Send RPC request  
    private String call(String message) throws IOException, InterruptedException {
        //Generate a unique string as the number of the callback queue
        String corrId = UUID.randomUUID().toString();
        //Send a request message, the message uses two attributes: replyto and correlationId
        //The server returns the result according to the replyto, and the client judges whether the response is for itself according to the correlationId
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName)
                .build();

        //Publish a message, requestQueueName routing rules
        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        //Since our consumer transaction processing is performed in a separate thread, we need to suspend the main thread before the response arrives.
        //Here we created a blocking queue ArrayBlockingQueue with a capacity of 1, because we only need to wait for a response.
        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        // String basicConsume(String queue, boolean autoAck, Consumer callback)
        channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                //Check whether its correlationId is the one we are looking for
                if (properties.getCorrelationId().equals(corrId)) {
                    //If yes, respond to BlockingQueue
                    response.offer(new String(body, "UTF-8"));
                }
            }
        });

        return response.take();
    }

    public void close() throws IOException {
        connection.close();
    }

    @Override
    public void deletePerson(Long personId) {
        try {
            String message = objectMapper.writeValueAsString(new Message(
                    new Person(personId, null, null, null), DELETE));
            String response = call(message);
            if (response.equals("0")) {
                Logger.getLogger(PersonApiClient.class.getName())
                        .log(Level.INFO, "Delete was unsuccessfull. Reason: no such person with id={0}", personId);
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(PersonApiClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PersonApiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        try {
            String message = objectMapper.writeValueAsString(new Message(
                    new Person(personId, firstName, lastName, middleName), SAVE));
            call(message);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(PersonApiClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PersonApiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Person findPerson(Long personId) {
        Person person = null;

        try (PreparedStatement ps = dbConn.prepareStatement(Query.GET);) {
            ps.setLong(1, personId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                long id = rs.getLong(1);
                String name = rs.getString(2);
                String lastName = rs.getString(3);
                String middleName = rs.getString(4);

                person = new Person(id, name, lastName, middleName);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonApiImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
        }

        return person;
    }

    @Override
    public List<Person> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        //DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
