package com.mamoon.simulator;

import java.util.Comparator;

public class SortTasksByRequestedTime implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        return o1.getRequestedTime()- o2.getRequestedTime();
    }
}
