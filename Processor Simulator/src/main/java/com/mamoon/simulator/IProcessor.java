package com.mamoon.simulator;

public interface IProcessor {
    int getID();
    void setBusy(boolean busy);
    boolean isBusy();
    void executeTask(Task task);
    Task getAttachedTask();
    void completeTask();
}
