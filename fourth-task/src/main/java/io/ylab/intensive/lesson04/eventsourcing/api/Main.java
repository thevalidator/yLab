/*
 * Copyright (C) 2023 thevalidator
 */

package io.ylab.intensive.lesson04.eventsourcing.api;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Main {
    public static void main(String[] args) {
        PersonApiClient clientRpc = null;
        String response = null;
        try {
            clientRpc = new PersonApiClient();

            System.out.println(" [x] Requesting fib(30)");
            response = clientRpc.call("10");
            System.out.println(" [.] Got '" + response + "'");
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (clientRpc != null) {
                try {
                    clientRpc.close();
                } catch (IOException _ignore) {
                }
            }
        }
    }
}
