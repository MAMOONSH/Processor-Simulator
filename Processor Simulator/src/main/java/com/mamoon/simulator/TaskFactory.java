package com.mamoon.simulator;

public class TaskFactory {
    private static int taskNumber=0;
    public Task taskCreator(String priority,Cycle creationCycle,int requestedTime)
    {
        if (creationCycle==null||priority==null)
            throw new NullPointerException("thrown at taskFactory");
        if(priority.equalsIgnoreCase("low"))
            return new LowPriorityTask(++taskNumber,creationCycle,requestedTime);
        if(priority.equalsIgnoreCase("high"))
            return new HighPriorityTask(++taskNumber,creationCycle,requestedTime);
        return null;
    }
}
