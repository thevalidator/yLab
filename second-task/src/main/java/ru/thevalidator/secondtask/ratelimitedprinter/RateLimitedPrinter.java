/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.secondtask.ratelimitedprinter;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class RateLimitedPrinter {
    
    private long timestamp;
    private final int interval;

    public RateLimitedPrinter(int interval) {
        timestamp = 0;
        this.interval = interval;
    }

    public void print(String message) {
        if (interval <= 0 || System.currentTimeMillis() - timestamp > interval) {
            System.out.println(message);
            timestamp = System.currentTimeMillis();
        }
    }

}
