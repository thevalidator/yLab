/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.secondtask.snilsvalidator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class SnilsValidatorDemo {
    public static void main(String[] args) {
        
        SnilsValidator validator = new SnilsValidatorImpl();

        System.out.println(validator.validate(null));           //false
        System.out.println(validator.validate("00000000000"));  //true
        System.out.println(validator.validate("12345678910"));  //false
        System.out.println(validator.validate("08765430300"));  //true
        System.out.println(validator.validate("18765430300"));  //false
        
        System.out.println(validator.validate("16675209900"));  //true
        System.out.println(validator.validate("06682514381"));  //true
        System.out.println(validator.validate("12602903624"));  //true
        System.out.println(validator.validate("15278361414"));  //false
        
    }
}
