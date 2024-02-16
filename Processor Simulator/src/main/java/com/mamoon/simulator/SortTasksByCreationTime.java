package com.mamoon.simulator;

import java.util.Comparator;

public class SortTasksByCreationTime implements Comparator<FutureTask> {
    @Override
    public int compare(FutureTask o1, FutureTask o2) {
        return o1.getCreationTime()-o2.getCreationTime();
    }
}
