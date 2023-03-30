package io.ylab.intensive.lesson04.eventsourcing.db;

import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.eventsourcing.db.service.BrokerService;
import io.ylab.intensive.lesson04.eventsourcing.db.service.DbHandler;
import io.ylab.intensive.lesson04.eventsourcing.db.service.impl.BrokerServiceImpl;
import io.ylab.intensive.lesson04.eventsourcing.db.service.impl.DbHandlerImpl;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbApp {

    public static void main(String[] args) throws Exception {
        
        try {
            
            DataSource dataSource = initDb();
            Connection dbConn = dataSource.getConnection();
            dbConn.setAutoCommit(true);
            
            DbHandler dbHandler = new DbHandlerImpl(dbConn);
            BrokerService brokerService = new BrokerServiceImpl(dbHandler);
            brokerService.start();            

        } catch (SQLException e) {
            Logger.getLogger(DbApp.class.getName()).log(Level.SEVERE, "ERROR", e);
            System.exit(1);
        }

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
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
    
}
