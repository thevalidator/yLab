/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.secondtask.statsaccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {
    
    int count;
    private Integer max;
    private Integer min;
    private long sum;

    public StatsAccumulatorImpl() {
        max = null;
        min = null;
        sum = 0;        
    }

    @Override
    public void add(int value) {
        sum = Math.addExact(sum, value);
        compareForMinAndMax(value);
        count++;
    }

    @Override
    public int getMin() {
        if (min == null) {
            return Integer.MAX_VALUE;
        } else {
            return min;
        }
    }

    @Override
    public int getMax() {
        if (max == null) {
            return Integer.MIN_VALUE;
        } else {
            return max;
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        return count == 0 ? 0. : ((double) sum) / count;
    }

    private void compareForMinAndMax(int value) {
        if (value >= getMax()) {
            max = value;
        }
        if (value <= getMin()) {
            min = value;
        }
    }

}
