/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.secondtask.complexnumbers;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class ComplexNumber {

    private final double real;
    private final double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber(double real) {
        this.real = real;
        this.imaginary = 0.;
    }

    public ComplexNumber add(ComplexNumber n) {
        double r = this.real + n.real;
        double i = this.imaginary + n.imaginary;
        return new ComplexNumber(r, i);
    }

    public ComplexNumber subtract(ComplexNumber n) {
        double r = this.real - n.real;
        double i = this.imaginary - n.imaginary;
        return new ComplexNumber(r, i);
    }

    public ComplexNumber multiply(ComplexNumber n) {
        double r = this.real * n.real - this.imaginary * n.imaginary;
        double i = this.real * n.imaginary + this.imaginary * n.real;
        return new ComplexNumber(r, i);
    }

    public double abs() {
        return abs(this);
    }

    @Override
    public String toString() {
        if (imaginary == 0) {
            return real + "";
        } else if (real == 0) {
            return imaginary + "i";
        } else if (imaginary < 0) {
            return real + " - " + (-imaginary) + "i";
        } else {
            return real + " + " + imaginary + "i";
        }
    }

    public static ComplexNumber add(ComplexNumber n1, ComplexNumber n2) {
        return new ComplexNumber(n1.real - n2.real, n1.imaginary - n2.imaginary);
    }
    
    public static ComplexNumber subtract(ComplexNumber n1, ComplexNumber n2) {
        return new ComplexNumber(n1.real - n2.real, n1.imaginary - n2.imaginary);
    }
    
    public static ComplexNumber multiply(ComplexNumber n1, ComplexNumber n2) {
        double r = (n1.real * n2.real) - (n1.imaginary * n2.imaginary);
        double i = (n1.real * n2.imaginary) + (n1.imaginary * n2.real);
        return new ComplexNumber(r, i);
    }
    
    public static double abs(ComplexNumber n) {
        return Math.hypot(n.real, n.imaginary);
    }

}
