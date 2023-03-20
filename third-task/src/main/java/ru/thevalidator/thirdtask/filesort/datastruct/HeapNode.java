/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.thirdtask.filesort.datastruct;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class HeapNode {
    
    private final int bufferNumber;
    private final long value;

    public HeapNode(int bufferNumber, long value) {
        this.bufferNumber = bufferNumber;
        this.value = value;
    }

    public int getBufferNumber() {
        return bufferNumber;
    }

    public long getValue() {
        return value;
    }

}
