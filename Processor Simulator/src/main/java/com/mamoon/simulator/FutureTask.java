package com.mamoon.simulator;

public class FutureTask {
    private final int creationTime;
    private final boolean isHighPriority;
    private final int requestedTime;

    public int getRequestedTime() {
        return requestedTime;
    }
    public FutureTask(int creationTime, boolean isHighPriority, int requestedTime) {
        this.creationTime = creationTime;
        this.isHighPriority = isHighPriority;
        this.requestedTime = requestedTime;
    }
    public int getCreationTime() {
        return creationTime;
    }
    public boolean isHighPriority() {
        return isHighPriority;
    }
}
