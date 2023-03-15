/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.secondtask.statsaccumulator;

import java.util.ArrayList;
import java.util.List;


public class StatsAccumulatorUsingListImpl implements StatsAccumulator {

    private List<Integer> numbers;
    private int maxIndex;
    private int minIndex;
    private long sum;

    public StatsAccumulatorUsingListImpl() {
        numbers = new ArrayList<>();
        maxIndex = -1;
        minIndex = -1;
        sum = 0;        
    }

    @Override
    public void add(int value) {
        numbers.add(value);
        sum = Math.addExact(sum, value);
        compareForMinAndMax(value);
    }

    @Override
    public int getMin() {
        if (minIndex < 0) {
            return Integer.MAX_VALUE;
        } else {
            return numbers.get(minIndex);
        }
    }

    @Override
    public int getMax() {
        if (maxIndex < 0) {
            return Integer.MIN_VALUE;
        } else {
            return numbers.get(maxIndex);
        }
    }

    @Override
    public int getCount() {
        return numbers.size();
    }

    @Override
    public Double getAvg() {
        return numbers.isEmpty() ? 0. : ((double) sum) / numbers.size();
    }

    private void compareForMinAndMax(int value) {
        if (value >= getMax()) {
            maxIndex = numbers.size() - 1;
        }
        if (value <= getMin()) {
            minIndex = numbers.size() - 1;
        }
    }

}
