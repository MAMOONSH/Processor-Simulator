package com.mamoon.simulator;

public class LowPriorityTask extends Task {
    public LowPriorityTask(int ID, Cycle creationCycle,int requestedTime) {
        super(ID, creationCycle,requestedTime);
    }
}
