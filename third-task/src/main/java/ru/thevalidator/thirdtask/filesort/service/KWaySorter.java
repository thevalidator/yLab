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
public class KWaySorter {

    private static final int NUMBER_OF_BUFFERS = 10;
    private static final int LONG_SIZE_IN_BYTES = 8;
    private final int LINES_PER_FILE;

    public KWaySorter() {
        long freeMemory = Runtime.getRuntime().freeMemory();
        long lines = freeMemory / LONG_SIZE_IN_BYTES;
        LINES_PER_FILE = freeMemory > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) lines;
    }

    public File sortFile(File dataFile) throws IOException {

        List<File> splittedFiles = splitFile(dataFile);

        //(K-way merge sort) works too slow, something wrong with the implementation
        File sortedFile = kWayMergeSort(splittedFiles);
        
        return sortedFile;
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
                buffersData.put(i, b.readLines(LINES_PER_FILE / NUMBER_OF_BUFFERS));
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
                            buffersData.put(node.getBufferNumber(), b.readLines(LINES_PER_FILE / NUMBER_OF_BUFFERS));
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

        try ( Reader reader = new FileReader(dataFile);  BufferedReader lnr = new BufferedReader(reader)) {

            String line = null;
            while ((line = lnr.readLine()) != null) {
                data.add(Long.valueOf(line));
                if (data.size() % LINES_PER_FILE == 0 || data.size() == Integer.MAX_VALUE) {
                    fileCounter++;
                    String filename = "tmp_" + fileCounter + ".tmp";
                    splittedFiles.add(writeDataToFile(filename, data));
                    data.clear();
                }
            }
            if (!data.isEmpty()) {
                String filename = "final.tmp";
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
        try ( PrintWriter pw = new PrintWriter(file)) {
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
