package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {

    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        // пишем взаимодействие с PersonApi
        
        List<Person> persons = personApi.findAll();
        for (Person p: persons) {
            System.out.printf("%d: %s %s %s\n", p.getId(), p.getName(), p.getLastName(), p.getMiddleName());
        }
        
        Person person = personApi.findPerson(11L);
        if (person != null) {
            System.out.printf("%d: %s %s %s\n", person.getId(), person.getName(), person.getLastName(), person.getMiddleName());
        } else {
            System.out.println("null");
        }
        personApi.deletePerson(111L);
        
        personApi.savePerson(333L, "George", "Vassermann", "Junior");
        
        TimeUnit.SECONDS.sleep(2);
        System.out.println();
        persons = personApi.findAll();
        for (Person p: persons) {
            System.out.printf("%d: %s %s %s\n", p.getId(), p.getName(), p.getLastName(), p.getMiddleName());
        }
        
        System.exit(0);

    }
}
