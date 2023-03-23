/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.thirdtask.passwordvalidator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class PasswordValidatorDemo {
    public static void main(String[] args) {
        
        // added limit for min length of login and password equals 3
        
        String login = "a1";
        String password = "pass_W0rd";
        String confirmPassword = "pass_W0rd";
        System.out.println(PasswordValidator.validate(login, password, confirmPassword));
    }
}
