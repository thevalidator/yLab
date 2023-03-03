/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.first.task;

import java.util.Random;
import java.util.Scanner;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Guess {

    public static void main(String[] args) throws Exception {
        int number = new Random().nextInt(100);
        int maxAttempts = 10;
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");

        Scanner sc = new Scanner(System.in);
        int counter = 0;
        boolean isGuessed = false;
        while (counter < maxAttempts) {
            counter++;
            int input = sc.nextInt();

            if (input != number) {
                if (input < number) {
                    System.out.printf("Моё число больше. Осталось %d попыток\n", maxAttempts - counter);
                } else {
                    System.out.printf("Моё число меньше. Осталось %d попыток\n", maxAttempts - counter);
                }
            } else {
                System.out.printf("Ты угадал число с %d-ой попытки\n", counter);
                isGuessed = true;
                break;
            }
        }

        if (!isGuessed) {
            System.out.println("Ты не угадал");
        }
        
    }

}
