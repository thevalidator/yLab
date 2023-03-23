/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.thirdtask.filesort.datastruct;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class MinHeap {

    private final int capacity;
    private final HeapNode[] array;
    private int size;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        array = new HeapNode[capacity];
        size = 0;
    }

    public void insert(HeapNode node) {
        if (size < capacity) {
            array[size] = node;
            sieveUp(size);
            size++;
        }
    }

    public HeapNode extract() {
        if (size == 0) {
            return null;
        }
        HeapNode extracted = array[0];
        array[0] = array[size - 1];
        sieveDown(0);
        size--;
        return extracted;
    }

    private void sieveUp(int index) {
        HeapNode inserted = array[index];
        int parentIndex = (index - 1) / 2;

        while (index > 0 && array[parentIndex].getValue() > inserted.getValue()) {
            array[index] = array[parentIndex];
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }

        array[index] = inserted;
    }

    private void sieveDown(int index) {
        int smallerIndex;
        HeapNode top = array[index];
        while (index < size / 2) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = leftChildIndex + 1;

            if (rightChildIndex < size && array[leftChildIndex].getValue() > array[rightChildIndex].getValue()) {
                smallerIndex = rightChildIndex;
            } else {
                smallerIndex = leftChildIndex;
            }

            if (top.getValue() <= array[smallerIndex].getValue()) {
                break;
            }

            array[index] = array[smallerIndex];
            index = smallerIndex;
        }
        array[index] = top;
    }

}
