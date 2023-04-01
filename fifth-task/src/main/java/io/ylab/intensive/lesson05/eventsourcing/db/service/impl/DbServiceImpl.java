/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson05.eventsourcing.db.service.impl;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.db.service.DbService;
import io.ylab.intensive.lesson05.eventsourcing.db.sql.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    @Override
    public void savePerson(Person p) {
        //System.out.println("SAVED " + p.getName());
        try (PreparedStatement ps = connection.prepareStatement(Query.SAVE);) {
            setValuesForSaveStatement(ps, p);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DbServiceImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deletePerson(Person p) {
        //System.out.println("DELETED " + p.getName());
        try (PreparedStatement ps = connection.prepareStatement(Query.DELETE);) {
            setValuesForDeleteStatement(ps, p);
            int result = ps.executeUpdate();
            if (result == 0) {
                Logger.getLogger(DbServiceImpl.class.getName())
                        .log(Level.INFO, "Delete was unsuccessfull. Reason: no such person with id={0}", p.getId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbServiceImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    private void setValuesForSaveStatement(PreparedStatement statement, Person person) throws SQLException {
        statement.setLong(1, person.getId());
        statement.setLong(8, person.getId());
        statement.setString(2, person.getName());
        statement.setString(5, person.getName());
        statement.setString(3, person.getLastName());
        statement.setString(6, person.getLastName());
        statement.setString(4, person.getMiddleName());
        statement.setString(7, person.getMiddleName());
    }

    private void setValuesForDeleteStatement(PreparedStatement statement, Person person) throws SQLException {
        statement.setLong(1, person.getId());
    }

}
