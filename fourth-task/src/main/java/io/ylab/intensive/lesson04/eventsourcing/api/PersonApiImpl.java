package io.ylab.intensive.lesson04.eventsourcing.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import java.util.List;

import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.api.sql.Query;
import static io.ylab.intensive.lesson04.eventsourcing.communication.message.ActionType.DELETE;
import static io.ylab.intensive.lesson04.eventsourcing.communication.message.ActionType.SAVE;
import io.ylab.intensive.lesson04.eventsourcing.communication.message.Message;
import static io.ylab.intensive.lesson04.eventsourcing.communication.routing.Data.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {

    private final Connection connection;
    private final Channel channel;
    private final BasicProperties props;
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public PersonApiImpl(Connection connection, Channel channel) {
        this.connection = connection;
        this.channel = channel;
        props = new AMQP.BasicProperties().builder()
                .deliveryMode(2)
                .contentType("application/json")
                .build();
    }

    @Override
    public void deletePerson(Long personId) {
        try {
            String message = objectMapper.writeValueAsString(new Message(
                    new Person(personId, null, null, null), DELETE));
            channel.basicPublish(EXCHANGE_NAME, DELETE_ROUTING_KEY, props, message.getBytes("UTF-8"));
        } catch (JsonProcessingException | UnsupportedEncodingException ex) {
            Logger.getLogger(PersonApiImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PersonApiImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        try {
            String message = objectMapper.writeValueAsString(new Message(
                    new Person(personId, firstName, lastName, middleName), SAVE));
            channel.basicPublish(EXCHANGE_NAME, SAVE_ROUTING_KEY, props, message.getBytes("UTF-8"));
        } catch (JsonProcessingException | UnsupportedEncodingException ex) {
            Logger.getLogger(PersonApiImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PersonApiImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Person findPerson(Long personId) {
        Person person = null;

        try (PreparedStatement ps = connection.prepareStatement(Query.GET);) {
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
        List<Person> persons = new ArrayList<>();
        
        try (Statement s = connection.createStatement();) {
            ResultSet rs = s.executeQuery(Query.GET_ALL);

            while (rs.next()) {
                long id = rs.getLong(1);
                String name = rs.getString(2);
                String lastName = rs.getString(3);
                String middleName = rs.getString(4);

                Person person = new Person(id, name, lastName, middleName);
                persons.add(person);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonApiImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        
        return persons;
    }

}
