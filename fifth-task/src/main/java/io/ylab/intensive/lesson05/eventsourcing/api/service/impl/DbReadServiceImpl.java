/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson05.eventsourcing.api.service.impl;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.api.service.DbReadService;
import io.ylab.intensive.lesson05.eventsourcing.api.sql.Query;
import jakarta.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class DbReadServiceImpl implements DbReadService {
    
    private final Connection connection;

    @Autowired
    public DbReadServiceImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
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
            Logger.getLogger(DbReadServiceImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
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
            Logger.getLogger(DbReadServiceImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        
        return persons;
    }
    
    @PreDestroy
    public void preDestroy() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbReadServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
