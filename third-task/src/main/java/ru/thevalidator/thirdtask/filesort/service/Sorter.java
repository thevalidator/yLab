/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.filesort.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Sorter {

    private static final int LONG_SIZE_IN_BYTES = 8;
    private final int LINES_PER_FILE;
    private final String SORTED_FILENAME = "sorted.txt";

    public Sorter() {
        long freeMemory = Runtime.getRuntime().freeMemory();
        long lines = freeMemory / LONG_SIZE_IN_BYTES;
        LINES_PER_FILE = freeMemory > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) lines;
    }

    // the size of the splitted files is set by constructor's parameter
    // beware can throw OutOfMemory exception in case of wrong value !!! 
    public Sorter(int linesPerFile) {
        this.LINES_PER_FILE = linesPerFile;
    }
    

    public File sortFile(File dataFile) throws IOException {

        List<File> splittedFiles = splitFile(dataFile);
        while (splittedFiles.size() != 1) {
            String filename = splittedFiles.size() == 2 ? SORTED_FILENAME : "merged" + System.currentTimeMillis();
            File megred = mergeTwoFiles(filename, splittedFiles.get(0), splittedFiles.get(1));
            File f1 = splittedFiles.remove(0);
            File f2 = splittedFiles.remove(0);
            deleteFile(f2);
            deleteFile(f1);
            splittedFiles.add(megred);
        }
        File sortedFile = splittedFiles.get(0);

        return sortedFile;
    }

    public File mergeTwoFiles(String filename, File file1, File file2) throws FileNotFoundException, IOException {
        File merged = new File(filename);
        try (BufferedReader br1 = new BufferedReader(new FileReader(file1)); 
                BufferedReader br2 = new BufferedReader(new FileReader(file2)); 
                PrintWriter pw = new PrintWriter(merged)) {
            
            mergeSort(br1, br2, pw);
            pw.flush();
        }
        return merged;

    }

    public void mergeSort(BufferedReader br1, BufferedReader br2, PrintWriter wr) throws IOException {

        String line1 = br1.readLine();
        String line2 = br2.readLine();

        while (line1 != null && line2 != null) {
            Long num1 = Long.valueOf(line1);
            Long num2 = Long.valueOf(line2);

            if (num1 >= num2) {
                wr.println(line2);
                line2 = br2.readLine();
            } else {
                wr.println(line1);
                line1 = br1.readLine();
            }
        }

        while (line1 != null) {
            Long num = Long.valueOf(line1);
            wr.println(num);
            line1 = br1.readLine();
        }

        while (line2 != null) {
            Long num = Long.valueOf(line2);
            wr.println(num);
            line2 = br2.readLine();
        }

    }

    private List<File> splitFile(File dataFile) {
        List<File> splittedFiles = new ArrayList<>();
        int fileCounter = 0;
        List<Long> data = new ArrayList<>();

        try (Reader reader = new FileReader(dataFile); BufferedReader lnr = new BufferedReader(reader)) {

            String line;
            while ((line = lnr.readLine()) != null) {
                data.add(Long.valueOf(line));
                if (data.size() % LINES_PER_FILE == 0 || data.size() == Integer.MAX_VALUE) {
                    fileCounter++;
                    String filename = "temp_" + fileCounter + ".tmp";
                    splittedFiles.add(writeDataToFile(filename, data));
                    data.clear();
                }
            }
            if (!data.isEmpty()) {
                String filename = fileCounter == 0 ? SORTED_FILENAME : "temp_final.tmp";
                splittedFiles.add(writeDataToFile(filename, data));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return splittedFiles;
    }

    private File writeDataToFile(String filename, List<Long> data) throws FileNotFoundException {
        Collections.sort(data);
        File file = saveToFile(filename, data);

        return file;
    }

    private File saveToFile(String name, List<Long> data) throws FileNotFoundException {
        File file = new File(name);
        try (PrintWriter pw = new PrintWriter(file)) {
            for (Long l : data) {
                pw.println(l);
            }
            pw.flush();
        }

        return file;
    }

    private void deleteFile(File file) {
        try {
            Files.delete(file.toPath());
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", file.toPath());
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", file.toPath());
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }

}
