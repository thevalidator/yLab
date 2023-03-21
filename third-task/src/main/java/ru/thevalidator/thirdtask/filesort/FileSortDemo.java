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
        
        File dataFile = new Generator().generate("data.txt", 375_000_000);  //new File("data.txt"); 
        System.out.println(new Validator(dataFile).isSorted()); // false
        
        // the size of the splitted files counts automaticly
        File sortedFile = new Sorter().sortFile(dataFile);
        
        
        // the size of the splitted files is set by constructor's parameter
        // !!! beware can throw OutOfMemory exception in case of wrong value !!! 
//        int linesCount = 100_000_000;
//        File sortedFile = new Sorter(linesCount).sortFile(dataFile);
        
        
        System.out.println(dataFile.length() == sortedFile.length());   // true
        System.out.println(new Validator(sortedFile).isSorted());       // true
        
    }

}
