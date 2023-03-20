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
    private final int index;

    public HeapNode(int bufferNumber, long value, int index) {
        this.bufferNumber = bufferNumber;
        this.value = value;
        this.index = index;
    }

    public int getBufferNumber() {
        return bufferNumber;
    }

    public long getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

}
