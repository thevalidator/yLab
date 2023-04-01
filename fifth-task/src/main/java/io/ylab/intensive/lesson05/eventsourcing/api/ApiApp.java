package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {

    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        // пишем взаимодействие с PersonApi
        List<Person> persons = personApi.findAll();
        for (Person person: persons) {
            System.out.printf("%d: %s %s %s\n", person.getId(), person.getName(), person.getLastName(), person.getMiddleName());
        }

    }
}
