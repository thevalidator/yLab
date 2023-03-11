/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.secondtask.sequences;

import java.math.BigInteger;

public class SequencesImpl implements Sequences {

    private static final int BASE = 1;

    @Override
    public void a(int n) {
        if (isPositive(n)) {
            long value = BASE + 1;
            int increment = 2;

            if (n > 0) {
                System.out.println(value);
                for (int i = 1; i < n; i++) {
                    value = value + increment;
                    System.out.println(value);
                }
            }
        }
    }

    @Override
    public void b(int n) {
        if (isPositive(n)) {
            long value = BASE;
            int increment = 2;

            if (n > 0) {
                System.out.println(value);
                for (int i = 1; i < n; i++) {
                    value = value + increment;
                    System.out.println(value);
                }
            }
        }
    }

    @Override
    public void c(int n) {
        if (isPositive(n)) {
            long value = BASE;
            long increment = 3;

            if (n > 0) {
                System.out.println(value);
                for (int i = 1; i < n; i++) {
                    value += increment;
                    increment += 2;
                    System.out.println(value);
                }
            }
        }
    }

    @Override
    public void d(int n) {
        if (isPositive(n)) {
            BigInteger value = BigInteger.valueOf(BASE);
            int exponent = 3;

            if (n > 0) {
                System.out.println(value);
                for (int i = 2; i < n; i++) {
                    value = BigInteger.valueOf(i).pow(exponent);
                    System.out.println(value);
                }
            }
        }
    }

    @Override
    public void e(int n) {
        if (isPositive(n)) {
            int value = BASE;

            if (n > 0) {
                System.out.println(value);
                for (int i = 1; i < n; i++) {
                    value = -value;
                    System.out.println(value);
                }
            }
        }
    }

    @Override
    public void f(int n) {
        if (isPositive(n)) {
            int value = BASE;

            if (n > 0) {
                System.out.println(value);
                for (int i = 1; i < n; i++) {
                    value = i % 2 == 0 ? (Math.abs(value) + 1) : -(Math.abs(value) + 1);
                    System.out.println(value);
                }
            }
        }
    }

    @Override
    public void g(int n) {
        if (isPositive(n)) {
            long value = BASE;
            long increment = 3;

            if (n > 0) {
                System.out.println(value);
                for (int i = 1; i < n; i++) {
                    long tmp = Math.abs(value) + increment;
                    value = i % 2 == 0 ? tmp : -tmp;
                    increment += 2;
                    System.out.println(value);
                }
            }
        }
    }

    @Override
    public void h(int n) {
        if (isPositive(n)) {
            int value = BASE;

            if (n > 0) {
                System.out.println(value);
                for (int i = 1; i < n; i++) {
                    if (i % 2 != 0) {
                        System.out.println("0");
                    } else {
                        value += 1;
                        System.out.println(value);
                    }
                }
            }
        }
    }

    @Override
    public void i(int n) {
        if (isPositive(n)) {
            BigInteger value = BigInteger.valueOf(BASE);
            long increment = 2;

            if (n > 0) {
                System.out.println(value);
                for (int i = 1; i < n; i++) {
                    value = value.multiply(BigInteger.valueOf(increment));
                    increment++;
                    System.out.println(value);
                }
            }
        }
    }

    @Override
    public void j(int n) {
        BigInteger value = BigInteger.ONE;
        BigInteger prev1 = BigInteger.ZERO;
        BigInteger prev2 = BigInteger.ONE;

        if (n > 0) {
            System.out.println(value);
            for (int i = 1; i < n; i++) {
                value = prev1.add(prev2);
                prev1 = prev2;
                prev2 = value;
                System.out.println(value);
            }
        }
    }

    private boolean isPositive(int n) {
        return n >= 0;
    }

}
