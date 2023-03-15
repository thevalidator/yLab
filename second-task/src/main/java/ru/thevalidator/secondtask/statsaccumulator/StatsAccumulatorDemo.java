/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.secondtask.statsaccumulator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class StatsAccumulatorDemo {

    public static void main(String[] args) {

        StatsAccumulator acc = new StatsAccumulatorImpl();

        System.out.println(">> EMPTY\n=================");
        System.out.printf("  TOTAL: %d\n", acc.getCount());
        System.out.printf("AVERAGE: %.3f\n", acc.getAvg());
        System.out.printf("    MIN: %d\n", acc.getMin());
        System.out.printf("    MAX: %d\n", acc.getMax());

        System.out.println("\n>> ADDED '0'\n=================");
        acc.add(0);
        System.out.printf("  TOTAL: %d\n", acc.getCount());
        System.out.printf("AVERAGE: %.3f\n", acc.getAvg());
        System.out.printf("    MIN: %d\n", acc.getMin());
        System.out.printf("    MAX: %d\n", acc.getMax());

        System.out.println("\n>> ADDED '10'\n=================");
        acc.add(10);
        System.out.printf("  TOTAL: %d\n", acc.getCount());
        System.out.printf("AVERAGE: %.3f\n", acc.getAvg());
        System.out.printf("    MIN: %d\n", acc.getMin());
        System.out.printf("    MAX: %d\n", acc.getMax());

        System.out.println("\n>> ADDED '-20'\n=================");
        acc.add(-20);
        System.out.printf("  TOTAL: %d\n", acc.getCount());
        System.out.printf("AVERAGE: %.3f\n", acc.getAvg());
        System.out.printf("    MIN: %d\n", acc.getMin());
        System.out.printf("    MAX: %d\n", acc.getMax());

        //  demonstrates protection from overflow (throws Arithmeticexception if accumulator overflows) 
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Integer.MAX_VALUE; j++) {
                acc.add(Integer.MAX_VALUE);
            }
        }

    }

}
