/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.filesort;

import java.io.File;
import java.io.IOException;
import ru.thevalidator.thirdtask.filesort.service.Generator;
import ru.thevalidator.thirdtask.filesort.service.Sorter;
import ru.thevalidator.thirdtask.filesort.service.Validator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class FileSortDemo {

    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("data.txt", 375_000_000);
        System.out.println(new Validator(dataFile).isSorted()); // false
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted()); // true
    }

}
