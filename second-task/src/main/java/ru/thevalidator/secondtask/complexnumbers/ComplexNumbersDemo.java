/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.secondtask.complexnumbers;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class ComplexNumbersDemo {
    public static void main(String[] args) {
        
        ComplexNumber n1 = new ComplexNumber(0.);
        ComplexNumber n2 = new ComplexNumber(1., 2.);
        ComplexNumber n3 = new ComplexNumber(3., -3.);
        
        System.out.println("n1      = " + n1);
        System.out.println("n2      = " + n2);
        System.out.println("n3      = " + n3);
        System.out.println("n1 * n2 = " + n1.multiply(n2));
        
        ComplexNumber n4 = new ComplexNumber(5.0, 6.0);
        ComplexNumber n5 = new ComplexNumber(-3.0, 4.0);

        System.out.println("n4      = " + n4);
        System.out.println("n5      = " + n5);
        System.out.println("n4 + n5 = " + n4.add(n5));
        System.out.println("a - b   = " + n4.subtract(n5));
        System.out.println("a * b   = " + n4.multiply(n5));
        System.out.println("|n4|    = " + n4.abs());
        
    }
}
