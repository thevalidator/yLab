package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import java.util.List;
import java.util.Scanner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {

    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        // пишем взаимодействие с PersonApi

        Scanner sc = new Scanner(System.in);
        System.out.println("AVAILABLE COMMANDS:\n"
                + "1)     SAVE PERSON: s id;name;lastName;middleName\n"
                + "2)   DELETE PERSON: d id\n"
                + "3)      GET PERSON: p id\n"
                + "4) GET ALL PERSONS: all\n"
                + "for EXIT type 'exit' or send empty line\n"
                + "\nEnter command:");

        String input;
        while (!(input = sc.nextLine()).equals("exit") && !input.isBlank()) {
            try {
                handleInput(input, personApi);
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
        System.exit(0);

    }

    private static void handleInput(String input, PersonApi api) {
        if (input.startsWith("s ")) {
            String[] data = input.substring(2).split(";");
            api.savePerson(Long.valueOf(data[0]), data[1], data[2], data[3]);
        } else if (input.startsWith("d ")) {
            api.deletePerson(Long.valueOf(input.substring(2)));
        } else if (input.startsWith("p ")) {
            Person p = api.findPerson(Long.valueOf(input.substring(2)));
            if (p != null) {
                printPerson(p);
            } else {
                System.out.println("No such person");
            }
        } else if (input.equals("all")) {
            List<Person> persons = api.findAll();
            if (persons.isEmpty()) {
                System.out.println("No persons in DB");
            } else {
                for (Person p: persons) {
                    printPerson(p);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static void printPerson(Person p) {
        System.out.printf("%d: %s %s %s\n", p.getId(), p.getName(), p.getLastName(), p.getMiddleName());
    }
}
