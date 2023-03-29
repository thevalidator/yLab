/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson04.eventsourcing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Main {
    public static void main(String[] args) throws Exception {
        
        PersonApiClient apiClient = null;
        try {
            //apiClient = new PersonApiClient();
            //response = clientRpc.call("{\"person\":{\"id\":76,\"name\":\"Margo\",\"lastName\":\"Williams\",\"middleName\":\"Jeremy\"},\"action\":\"DELETE\"}");
            //System.out.println(" [.] Got '" + response + "'");
            
            //apiClient.deletePerson(76L);
            //apiClient.savePerson(39L, "Sammy", "Morgan", "Boston");
            apiClient = new PersonApiClient();
            Scanner sc = new Scanner(System.in);
            System.out.println("AVAILABLE COMMANDS:\n"
                    + "1)     SAVE PERSON: s id;name;lastName;middleName\n"
                    + "2)   DELETE PERSON: d id\n"
                    + "3)      GET PERSON: p id\n"
                    + "4) GET ALL PERSONS: all\n"
                    + "5)            EXIT: exit\n"
                    + "\nEnter command:");

            while (true) {
                String input = sc.nextLine();
                try {
                    if (input.equals("exit")) {
                        break;
                    } else if (input.startsWith("s ")) {
                        String[] data = input.substring(2).split(";");
                        apiClient.savePerson(Long.valueOf(data[0]), data[1], data[2], data[3]);
                    } else if (input.startsWith("d ")) {
                        apiClient.deletePerson(Long.valueOf(input.substring(2)));
                    } else if (input.startsWith("p ")) {
                        Person person = apiClient.findPerson(Long.valueOf(input.substring(2)));
                        if (person != null) {
                            System.out.println(person);
                        } else {
                            System.out.println("No such person");
                        }
                    } else if (input.equals("all")) {
                        List<Person> persons = apiClient.findAll();
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
            
            
        } catch (IOException | TimeoutException  e) {
            Logger.getLogger(PersonApiClient.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (apiClient != null) {
                try {
                    apiClient.close();
                } catch (IOException _ignore) {
                }
            }
        }
    }
}
