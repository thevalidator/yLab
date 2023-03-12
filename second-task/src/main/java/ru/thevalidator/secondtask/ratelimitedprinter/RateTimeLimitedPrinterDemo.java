/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.secondtask.ratelimitedprinter;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class RateTimeLimitedPrinterDemo {

    public static void main(String[] args) {
        
        RateLimitedPrinter rateLimitedPrinter = new RateLimitedPrinter(1000);
        
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimitedPrinter.print(String.valueOf(i));
        }
        
    }
}
