package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.db.service.BrokerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbApp {
  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();
    // тут пишем создание и запуск приложения работы с БД
      BrokerService service = applicationContext.getBean(BrokerService.class);
      service.start();
  }
}
