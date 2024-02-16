package com.mamoon.simulator;

public class HighPriorityTask extends Task{
    public HighPriorityTask(int ID, Cycle creationCycle,int requestedTime) {
        super(ID, creationCycle,requestedTime);
    }
}
