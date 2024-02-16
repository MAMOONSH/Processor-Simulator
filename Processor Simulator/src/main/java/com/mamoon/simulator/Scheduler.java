package com.mamoon.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Scheduler {//todo template design pattern
    private final PriorityQueue<Task> waitingLowPriorityTasks =new PriorityQueue<>(2,new SortTasksByRequestedTime());
    private final PriorityQueue<Task> waitingHighPriorityTasks =new PriorityQueue<>(2,new SortTasksByRequestedTime());
    private final List<Task> executingTasks=new ArrayList<>();
    private final List<Task> completedTasks=new ArrayList<>();
    private List<IProcessor> processors;
    public Scheduler(){}
    public List<IProcessor> getProcessors() {
        return processors;
    }
    public void getProcessors(List<IProcessor> processors) {
        this.processors = processors;
    }
    public void addTaskToQueue(Task task) {
        if(task instanceof LowPriorityTask) {
            waitingLowPriorityTasks.add(task);
        }
        else if(task instanceof HighPriorityTask) {
            waitingHighPriorityTasks.add(task);
        }

    }
    public void scheduling(Cycle currentCycle) {
        cleanCompletedTasks(currentCycle);
        while((!waitingLowPriorityTasks.isEmpty()||!waitingHighPriorityTasks.isEmpty())&&gettingIdleProcessor()!=0) {
            attachTaskToProcess();
        }
        replaceTasks();
        cleanCompletedTasks(currentCycle);
        while((!waitingLowPriorityTasks.isEmpty()||!waitingHighPriorityTasks.isEmpty())&&gettingIdleProcessor()!=0) {
            attachTaskToProcess();
        }
    }
    private void cleanCompletedTasks(Cycle currentCycle) {
        List<Task> doneTasks=new ArrayList<>();
        for(int i=0;i<executingTasks.size();i++) {
            if(executingTasks.get(i).getRequestedTime()<=0) {
                taskDone(currentCycle, i,doneTasks);
            }
        }
        while(!doneTasks.isEmpty()) {
            for(int i=0;i<executingTasks.size();i++) {
                if(executingTasks.get(i).getID()==doneTasks.get(0).getID())
                {
                    executingTasks.remove(i);
                    doneTasks.remove(0);
                    break;
                }
            }
        }
    }

    private void taskDone(Cycle currentCycle, int i,List<Task> doneTasks) {
        for (IProcessor processor : processors) {
            if (processor.isBusy() && processor.getAttachedTask().getID() == executingTasks.get(i).getID()) {
                executingTasks.get(i).setState("completed");
                executingTasks.get(i).setCompletionCycle(currentCycle);
                doneTasks.add(executingTasks.get(i));
                completedTasks.add(executingTasks.get(i));
                processor.completeTask();
                break;
            }
        }
    }

    private int gettingIdleProcessor() {
        for (IProcessor processor : processors)
            if (!processor.isBusy())
                return processor.getID();
        return 0;
    }
    private void attachTaskToProcess()
    {
        if(!waitingHighPriorityTasks.isEmpty()) {
            processors.get(gettingIdleProcessor()-1).executeTask(waitingHighPriorityTasks.peek());
            assert waitingHighPriorityTasks.peek() != null;
            waitingHighPriorityTasks.peek().setState("executing");
            executingTasks.add(waitingHighPriorityTasks.poll());
        }
        else if(!waitingLowPriorityTasks.isEmpty()) {
            processors.get(gettingIdleProcessor()-1).executeTask(waitingLowPriorityTasks.peek());
            assert waitingLowPriorityTasks.peek() != null;
            waitingLowPriorityTasks.peek().setState("executing");
            executingTasks.add(waitingLowPriorityTasks.poll());
        }
    }
    private void replaceTasks()
    {
        if(!waitingHighPriorityTasks.isEmpty()) {
            for (int i = 0; i < processors.size(); i++) {
                if(waitingHighPriorityTasks.isEmpty()) break;
                if(processors.get(i).isBusy()&&processors.get(i).getAttachedTask() instanceof LowPriorityTask) {
                    swappingTaskAtProcessor(i);
                }
            }
        }
    }

    private void swappingTaskAtProcessor(int i) {
        Task taskAtProcessor=processors.get(i).getAttachedTask();
        returnTaskFromExecutingToWaiting(taskAtProcessor);
        processors.get(i).setBusy(false);
        attachTaskToProcess();
    }

    private void returnTaskFromExecutingToWaiting(Task taskAtProcessor) {
        waitingLowPriorityTasks.add(taskAtProcessor);
        for(int j=0;j<executingTasks.size();j++) {
            if(executingTasks.get(j).getID()== taskAtProcessor.getID())
                executingTasks.remove(j);
        }
    }

    public List<Task> getCompletedTasks() {
        return completedTasks;
    }
}
