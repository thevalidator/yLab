/*
 * Copyright (C) 2023 thevalidator
 */
package io.ylab.intensive.lesson04.eventsourcing.db.service.impl;

import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.db.service.DbHandler;
import io.ylab.intensive.lesson04.eventsourcing.db.sql.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbHandlerImpl implements DbHandler {

    private final Connection connection;

    public DbHandlerImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void savePerson(Person p) {
        try (PreparedStatement ps = connection.prepareStatement(Query.SAVE);) {
            setValuesForSaveStatement(ps, p);
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DbHandlerImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
    }

    @Override
    public void deletePerson(Person p) {
        try (PreparedStatement ps = connection.prepareStatement(Query.DELETE);) {
            setValuesForDeleteStatement(ps, p);
            int result = ps.executeUpdate();
            if (result == 0) {
                Logger.getLogger(DbHandlerImpl.class.getName())
                        .log(Level.INFO, "Delete was unsuccessfull. Reason: no such person with id={0}", p.getId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbHandlerImpl.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
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

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(DbHandlerImpl.class.getName()).log(Level.SEVERE, e.getMessage());
            }
        }
    }

}
