/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.first.task;

import java.util.Scanner;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Stars {

    public static void main(String[] args) throws Exception {
        
        try ( Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();
            
            for (int i = 0; i < n; i++) {
                System.out.print(template);
                for (int j = 1; j < m; j++) {
                    System.out.print(" " + template);
                }
                System.out.println();
            }
        }

    }
}
