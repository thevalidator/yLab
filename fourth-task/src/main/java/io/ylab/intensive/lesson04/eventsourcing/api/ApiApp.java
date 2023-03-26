package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.api.service.Send;

public class ApiApp {
    
    

    public static void main(String[] args) throws Exception {
        
        //docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq
        //docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.11-management

        ConnectionFactory connectionFactory = initMQ();
        
        Send.sendMessage(connectionFactory, "Hi there!");
        Send.sendMessage(connectionFactory, "It's me illusion is here!");
        for (int i = 0; i < 5; i++) {
            Send.sendMessage(connectionFactory, "....");
            
        }
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}
