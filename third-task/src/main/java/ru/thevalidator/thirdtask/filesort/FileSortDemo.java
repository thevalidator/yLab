/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.filesort;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import ru.thevalidator.thirdtask.filesort.buffer.Buffer;
import ru.thevalidator.thirdtask.filesort.datastruct.HeapNode;
import ru.thevalidator.thirdtask.filesort.datastruct.MinHeap;
import ru.thevalidator.thirdtask.filesort.service.Generator;
import ru.thevalidator.thirdtask.filesort.service.Sorter;
import ru.thevalidator.thirdtask.filesort.service.Validator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class FileSortDemo {

    public static void main(String[] args) throws IOException {
        
//        Buffer b = new Buffer(new File("final.tmp"));
//        
//        List<Long> l1 = b.readLines(2);
//        System.out.println("> " + l1.size());
//        for (Long l : l1) {
//            System.out.println(l);
//        }
//        List<Long> l2 = b.readLines(12);
//        System.out.println("> " + l2.size());
//        for (Long l : l2) {
//            System.out.println(l);
//        }
//        System.out.println(b.isRead());
        //MinHeap h = new MinHeap(10);

        //
//        tmp_1.tmp
//        tmp_2.tmp
//        tmp_3.tmp
//        tmp_4.tmp
//        tmp_5.tmp
//        tmp_6.tmp
//        tmp_7.tmp
//        tmp_8.tmp
//        tmp_9.tmp
//        tmp_10.tmp
//        tmp_11.tmp
//        final.tmp
        //
        
        
        
        
        //File dataFile = new Generator().generate("data.txt", 375_000_000);
        //File dataFile = new Generator().generate("data.txt", 1100);
        File dataFile = new File("data.txt");
        //File dataFile = new File("D:/Win10_20H2_v2_EnglishInternational_x64.iso");
        
        new Sorter().sortFile(dataFile);
        
        //System.out.println(new Validator(dataFile).isSorted()); // false
        //File sortedFile = new Sorter().sortFile(dataFile);
        //System.out.println(new Validator(sortedFile).isSorted()); // true
        
////        try ( Reader reader = new FileReader(dataFile);  LineNumberReader lineNumberReader = new LineNumberReader(reader)) {
////
////            System.out.println("Start Line Number: " + lineNumberReader.getLineNumber());
////
////            System.out.println(" ----- ");
////
////            String line = null;
////            while ((line = lineNumberReader.readLine()) != null) {
////
////                System.out.println("Line Number: " + lineNumberReader.getLineNumber());
////                System.out.println("  Line Content: " + line);
////                
////                if (lineNumberReader.getLineNumber() == 10) {
////                    break;
////                }
////            }
////        } catch (Exception e) {
////
////        }
        
    }

}
