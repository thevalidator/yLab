package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import static io.ylab.intensive.lesson04.eventsourcing.communication.routing.Data.EXCHANGE_NAME;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApiApp {

    public static void main(String[] args) throws Exception {

        ConnectionFactory connectionFactory = initMQ();

        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);

            PersonApi api = new PersonApiImpl(DbUtil.buildDataSource().getConnection(), channel);

            Scanner sc = new Scanner(System.in);
            System.out.println("AVAILABLE COMMANDS:\n"
                    + "1)     SAVE PERSON: s id;name;lastName;middleName\n"
                    + "2)   DELETE PERSON: d id\n"
                    + "3)      GET PERSON: p id\n"
                    + "4) GET ALL PERSONS: all\n"
                    + "5)            EXIT: exit\n"
                    + "\nEnter command:");

            String input;
            while (!(input = sc.nextLine()).equals("exit")) {
                try {
                    handleInput(input, api);
                } catch (Exception e) {
                    if (e instanceof IllegalArgumentException
                            || e instanceof NumberFormatException
                            || e instanceof ArrayIndexOutOfBoundsException) {
                        System.err.println("Wrong input, try again");
                    } else {
                        System.err.println(e.getMessage());
                    }
                }
            }
            System.out.println("GOODBYE!");

        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(ApiApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static void handleInput(String input, PersonApi api) {
        if (input.startsWith("s ")) {
            String[] data = input.substring(2).split(";");
            api.savePerson(Long.valueOf(data[0]), data[1], data[2], data[3]);
        } else if (input.startsWith("d ")) {
            api.deletePerson(Long.valueOf(input.substring(2)));
        } else if (input.startsWith("p ")) {
            Person person = api.findPerson(Long.valueOf(input.substring(2)));
            if (person != null) {
                System.out.println(person);
            } else {
                System.out.println("No such person");
            }
        } else if (input.equals("all")) {
            List<Person> persons = api.findAll();
            if (persons.isEmpty()) {
                System.out.println("No persons in DB");
            } else {
                for (Person person: persons) {
                    System.out.println(person);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
