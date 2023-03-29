package io.ylab.intensive.lesson04.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import java.sql.SQLException;
import javax.sql.DataSource;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.communication.message.ActionType;
import io.ylab.intensive.lesson04.eventsourcing.communication.message.Message;
import io.ylab.intensive.lesson04.eventsourcing.communication.routing.Data;
import static io.ylab.intensive.lesson04.eventsourcing.communication.routing.Data.QUEUE_NAME;
import io.ylab.intensive.lesson04.eventsourcing.db.service.DbHandler;
import io.ylab.intensive.lesson04.eventsourcing.db.service.impl.DbHandlerImpl;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbApp {

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        Connection dbConn = null;
        com.rabbitmq.client.Connection brokerConn = null;
        Channel channel = null;
        try {
            DataSource dataSource = initDb();
            dbConn = dataSource.getConnection();
            dbConn.setAutoCommit(true);
            DbHandler dbHandler = new DbHandlerImpl(dbConn);
            ConnectionFactory connectionFactory = initMQ();
            brokerConn = connectionFactory.newConnection();
            channel = brokerConn.createChannel();
            channel.exchangeDeclare(Data.EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);
            String queueName = QUEUE_NAME;
            String bindingKey = "db.person.*";
            
            Map<String, Object> arguments = new HashMap<>();
            arguments.put("x-max-length", 10_000);              //max queue length
            //arguments.put("x-queue-mode", "lazy");
            
            channel.queueDeclare(queueName, true, false, false, arguments);
            channel.queueBind(queueName, Data.EXCHANGE_NAME, bindingKey);
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);

            Logger.getLogger(DbApp.class.getName()).log(Level.INFO, " [*] App started, waiting for messages...");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String incomingMessage = new String(delivery.getBody(), StandardCharsets.UTF_8);
                try {
                    Message message = mapper.readValue(incomingMessage, Message.class);
                    if (message.getAction().equals(ActionType.SAVE)) {
                        dbHandler.savePerson(message.getPerson());
                    } else {
                        dbHandler.deletePerson(message.getPerson());
                    }
                } catch (JsonProcessingException ex) {
                    Logger.getLogger(DbApp.class.getName()).log(Level.SEVERE, ex.getMessage());
                }
            };
            boolean isHandled = true;
            channel.basicConsume(queueName, isHandled, deliverCallback, consumerTag -> {
            });

        } catch (Exception e) {
            Logger.getLogger(DbApp.class.getName()).log(Level.SEVERE, "ERROR", e);
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            if (brokerConn != null && brokerConn.isOpen()) {
                brokerConn.close();
            }
            if (dbConn != null && !dbConn.isClosed()) {
                dbConn.close();
            }
            System.exit(1);
        }

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
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
