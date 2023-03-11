/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.secondtask.sequences;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class SequencesDemo {

    public static void main(String[] args) {
        
        System.out.println("""
                           ***********************************
                           ****  SEQUENCE GENERATOR DEMO  ****
                           ***********************************""");
        Sequences seq = new SequencesImpl();
        
        System.out.println("\n== SEQUENCE A ==");
        seq.a(10);
        System.out.println("\n== SEQUENCE B ==");
        seq.b(10);
        System.out.println("\n== SEQUENCE C ==");
        seq.c(10);
        System.out.println("\n== SEQUENCE D ==");
        seq.d(10);
        System.out.println("\n== SEQUENCE E ==");
        seq.e(10);
        System.out.println("\n== SEQUENCE F ==");
        seq.f(10);
        System.out.println("\n== SEQUENCE G ==");
        seq.g(10);
        System.out.println("\n== SEQUENCE H ==");
        seq.h(10);
        System.out.println("\n== SEQUENCE I ==");
        seq.i(10);
        System.out.println("\n== SEQUENCE J ==");
        seq.j(10);

    }
}
