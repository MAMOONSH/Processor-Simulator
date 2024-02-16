package com.mamoon.simulator;

public class Processor implements IProcessor {
    private final int ID;
    private boolean isBusy=false;
    private Task attachedTask=null;
    @Override
    public int getID() {
        return ID;
    }
    @Override
    public void setBusy(boolean busy) {
        isBusy = busy;
    }
    @Override
    public boolean isBusy() {
        return isBusy;
    }

    public Processor(int ID) {
        this.ID = ID;
    }
    @Override
    public void executeTask(Task task)
    {
        if(task==null)
            throw new NullPointerException();
        attachedTask=task;
        setBusy(true);
    }
    @Override
    public void completeTask()
    {
        attachedTask=null;
        setBusy(false);
    }

    @Override
    public String toString() {
        String task=isBusy?attachedTask.toString():"";
        return "Processor{" +
                "ID=" + ID +
                ", isBusy=" + isBusy +
                task+
                '}';
    }
    @Override
    public Task getAttachedTask() {
        return attachedTask;
    }
}
