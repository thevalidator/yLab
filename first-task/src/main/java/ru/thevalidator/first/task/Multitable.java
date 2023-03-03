/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.first.task;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Multitable {
    public static void main(String[] args) {
        int start = 1;
        int finish = 9;
        
        for (int i = start; i <= finish; i++) {
            for (int j = start; j <= finish; j++) {
                System.out.printf("%d * %d = %2d\n", i, j, (i * j));
            }
            System.out.println();
        }
    }
}
