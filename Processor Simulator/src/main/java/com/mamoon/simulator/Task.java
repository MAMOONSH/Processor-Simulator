package com.mamoon.simulator;

public abstract class Task{
    private enum State{ waiting,executing,completed}
    State state=State.waiting;
    private final int  ID;
    private final Cycle creationCycle;
    private Cycle completionCycle;
    private int requestedTime;

    public Task(int ID, Cycle creationCycle, int requestedTime) {
        this.ID = ID;
        this.creationCycle = creationCycle;
        this.requestedTime = requestedTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "state=" + state +
                ", ID=" + ID +
                ", creationCycle=" + creationCycle.getID() +
                ", requestedTime=" + requestedTime +
                '}';
    }

    public int getID() {
        return ID;
    }

    public Cycle getCreationCycle() {
        return creationCycle;
    }

    public Cycle getCompletionCycle() {
        return completionCycle;
    }

    public void setCompletionCycle(Cycle completionCycle) {
        this.completionCycle = completionCycle;
    }

    public int getRequestedTime() {
        return requestedTime;
    }

    public void decrementRequestedTime() {
        this.requestedTime =this.requestedTime-1 ;
    }

    public void setState(String state)
    {
        if(state.equalsIgnoreCase("waiting"))
            this.state=State.waiting;
        if(state.equalsIgnoreCase("executing"))
            this.state=State.executing;
        if(state.equalsIgnoreCase("completed"))
            this.state=State.completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return ID == task.ID;
    }

}
