/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.secondtask.snilsvalidator;

public class SnilsValidatorImpl implements SnilsValidator {

    @Override
    public boolean validate(String snils) {
        boolean result = false;

        if (validFormat(snils)) {
            int sum = getSum(snils);
            int checksum = getChecksum(sum);
            result = checksum == Integer.parseInt(snils.substring(9));
        }

        return result;
    }

    public int getChecksum(int sum) {
        int checksum = 0;

        if (sum < 100) {
            checksum = sum;
        } else if (sum > 100) {
            int remain = sum % 101;
            if (remain != 100) {
                checksum = remain;
            }
        }

        return checksum;
    }

    public int getSum(String snils) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int number = Character.getNumericValue(snils.charAt(i));
            sum += number * (9 - i);
        }

        return sum;
    }

    private boolean validFormat(String snils) {
        return snils != null && snils.length() == 11 && snils.matches("[0-9]+");
    }

}
