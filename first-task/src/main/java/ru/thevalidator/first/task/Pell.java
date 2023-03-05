/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.first.task;

import java.util.Scanner;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Pell {

    public static void main(String[] args) throws Exception {
        try ( Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();

            long prev2 = 0;
            long prev1 = 1;

            if (n == 0) {
                System.out.println(prev2);
            } else if (n == 1) {
                System.out.println(prev1);
            } else {
                long tmp = 0;
                for (int i = 1; i < n; i++) {
                    tmp = prev1 * 2 + prev2;
                    prev2 = prev1;
                    prev1 = tmp;
                }
                System.out.println(tmp);
            }

        }
    }

}
