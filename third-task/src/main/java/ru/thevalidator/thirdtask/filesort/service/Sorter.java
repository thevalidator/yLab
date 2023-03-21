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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.thevalidator.thirdtask.filesort.buffer.Buffer;
import ru.thevalidator.thirdtask.filesort.datastruct.HeapNode;
import ru.thevalidator.thirdtask.filesort.datastruct.MinHeap;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Sorter {

    private static final int NUMBERS_PER_FILE = 375_000_000 / 10;
    private static final int NUMBER_OF_BUFFERS = 10;

    public File sortFile(File dataFile) throws IOException {

        List<File> splittedFiles = splitFile(dataFile);
        
        // option 1
        while (splittedFiles.size() != 1) {
            File megred = mergeTwoFiles(splittedFiles.get(0), splittedFiles.get(1));
            File f1 = splittedFiles.remove(0);
            File f2 = splittedFiles.remove(0);
            deleteFile(f2);
            deleteFile(f1);
            splittedFiles.add(megred);
        }
        File sortedFile = splittedFiles.get(0);
                
        // option 2
        //File sortedFile = kWayMergeSort(splittedFiles);


        return sortedFile;
    }

    public File mergeTwoFiles(File file1, File file2) throws FileNotFoundException, IOException {
        File merged = new File("merged_" + System.currentTimeMillis());
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

    public File kWayMergeSort(List<File> files) throws FileNotFoundException, IOException {

        while (files.size() > 1) {
            List<Buffer> buffers = new ArrayList<>();
            Map<Integer, List<Long>> buffersData = new HashMap<>();

            for (int i = 0; i < NUMBER_OF_BUFFERS; i++) {
                if (files.isEmpty()) {
                    break;
                }
                Buffer b = new Buffer(files.get(0));
                buffers.add(b);
                buffersData.put(i, b.readLines(NUMBERS_PER_FILE / NUMBER_OF_BUFFERS));
                files.remove(0);
            }

            MinHeap heap = new MinHeap(buffersData.size());

            for (Map.Entry<Integer, List<Long>> entry : buffersData.entrySet()) {
                heap.insert(new HeapNode(entry.getKey(), entry.getValue().get(0), 0));
            }

            File merged = new File("merged_" + System.currentTimeMillis());
            try (PrintWriter pw = new PrintWriter(merged)) {
                HeapNode node;
                while ((node = heap.extract()) != null) {
                    pw.println(node.getValue());

                    int index = node.getIndex() + 1;
                    if (index < buffersData.get(node.getBufferNumber()).size()) {
                        heap.insert(new HeapNode(node.getBufferNumber(), buffersData.get(node.getBufferNumber()).get(index), index));
                    } else {
                        Buffer b = buffers.get(node.getBufferNumber());
                        if (!b.isRead()) {
                            buffersData.put(node.getBufferNumber(), b.readLines(NUMBERS_PER_FILE / NUMBER_OF_BUFFERS));
                            heap.insert(new HeapNode(node.getBufferNumber(), buffersData.get(node.getBufferNumber()).get(0), 0));
                        } else {
                            deleteFile(b.getFile());
                            //buffers.remove(b);
                        }
                    }
                }
                pw.flush();
            }
            files.add(merged);
        }

        return files.get(0);
    }

    private List<File> splitFile(File dataFile) {
        List<File> splittedFiles = new ArrayList<>();
        int fileCounter = 0;
        List<Long> data = new ArrayList<>();

        try (Reader reader = new FileReader(dataFile); BufferedReader lnr = new BufferedReader(reader)) {

            String line = null;
            while ((line = lnr.readLine()) != null) {
                data.add(Long.valueOf(line));
                if (data.size() % NUMBERS_PER_FILE == 0) {
                    fileCounter++;
                    String filename = "tmp_" + fileCounter + ".tmp";
                    splittedFiles.add(writeFile(filename, data));
                    data.clear();
                }
            }
            if (!data.isEmpty()) {
                String filename = "final.tmp";
                splittedFiles.add(writeFile(filename, data));
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return splittedFiles;
    }

    private File writeFile(String filename, List<Long> data) throws FileNotFoundException {
        Collections.sort(data);
        File file = saveToFile(filename, data);
        data.clear();

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
