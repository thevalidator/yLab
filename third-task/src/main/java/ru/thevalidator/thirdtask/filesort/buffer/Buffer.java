/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.thirdtask.filesort.buffer;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Buffer {
    
    private final File file;
    private long position;
    private final long size;

    public Buffer(File file) {
        this.file = file;
        position = 0;
        size = file.length();
    }

//    public File getFile() {
//        return file;
//    }
//
//    public long getPosition() {
//        return position;
//    }
    
    public boolean isRead() {
        return position == size;
    }
    
    public List<Long> readLines(int number) {
        List<Long> list = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {

            String line = null;
            raf.seek(position);
            
            while (list.size() < number && (line = raf.readLine()) != null) {
                list.add(Long.valueOf(line));
            }
            position = raf.getFilePointer();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return list;
    }

}
