/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.filesort.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.nio.file.Files;
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

    private static final int NUMBERS_PER_FILE = 100;
    private static final int NUMBER_OF_BUFFERS = 8;
    private static final int BUFFER_SIZE = 1024 * 8;

    public File sortFile(File dataFile) throws IOException {

        //File sortedFile = new File("sorted_data.txt");
        //String tmpPath = "tmp" + File.separator;
        //File curFile = new File(tempDirectory + "temp" + (++fileCount));
        List<File> splittedFiles = splitFile(dataFile);
        mergeSortedFiles(null, splittedFiles);

//        try(Reader reader = new FileReader(dataFile);
//                LineNumberReader lineNumberReader = new LineNumberReader(reader)){
//            
//            System.out.println("Start Line Number: " + lineNumberReader.getLineNumber());
//            
//            System.out.println(" ----- ");
//
//            String line = null;
//            while((line  = lineNumberReader.readLine()) != null) {  
//                
//                System.out.println("Line Number: " + lineNumberReader.getLineNumber());
//                System.out.println("  Line Content: " + line);
//            }
//        } catch (Exception e) {
//            
//        }
        return null;
    }

    public File mergeSortedFiles(File mergedFile, List<File> files) throws FileNotFoundException, IOException {

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
            try ( PrintWriter pw = new PrintWriter(merged)) {
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
                        }
                    }
                    //heap.insert(new HeapNode(node.getBufferNumber(), buffersData.get(node.getBufferNumber()).get(index), index));
                }
                pw.flush();
            }
            files.add(merged);
        }

        return null;
    }

    private List<File> splitFile(File dataFile) {
        List<File> splittedFiles = new ArrayList<>();
        int fileCounter = 0;
        List<Long> data = new ArrayList<>();

        try ( Reader reader = new FileReader(dataFile);  BufferedReader lnr = new BufferedReader(reader)) {

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
            System.out.println(e.getMessage());
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
        try ( PrintWriter pw = new PrintWriter(file)) {
            for (Long l : data) {
                pw.println(l);
            }
            pw.flush();
        }

        return file;
    }

    private void deleteFile(File file) {
        // TODO:
    }

}
