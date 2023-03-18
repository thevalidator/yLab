/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.transliterator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class TransliteratorDemo {

    public static void main(String[] args) {
        
        String text1 = "HELLO! ПРИВЕТ! Go, boy!";
        String text2 = "HELLO! ПРИВЕТЬ! Go, boy!";
        String text3 = "";
        String text4 = null;
        String text5 = "опа, Я Ж ГОВОРИЛЪ!";
        
        Transliterator transliterator = new TransliteratorImpl();
        
        String res1 = transliterator.transliterate(text1);
        System.out.println("text before: " + text1);
        System.out.println("text  after: " + res1);
        System.out.println();
        
        String res2 = transliterator.transliterate(text2);
        System.out.println("text before: " + text2);
        System.out.println("text  after: " + res2);
        System.out.println();
        
        String res3 = transliterator.transliterate(text3);
        System.out.println("text before: " + text3);
        System.out.println("text  after: " + res3);
        System.out.println();
        
        String res4 = transliterator.transliterate(text4);
        System.out.println("text before: " + text4);
        System.out.println("text  after: " + res4);
        System.out.println();
        
        String res5 = transliterator.transliterate(text5);
        System.out.println("text before: " + text5);
        System.out.println("text  after: " + res5);
        
    }
}
