/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.secondtask.snilsvalidator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface SnilsValidator {

    /**
     * Проверяет, что в строке содержится валидный номер СНИЛС
     *
     * @param snils снилс
     * @return результат проверки
     */
    boolean validate(String snils);

}
