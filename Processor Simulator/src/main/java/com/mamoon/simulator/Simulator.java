package com.mamoon.simulator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Simulator {
    private static Simulator simulator=null;
    private final List<FutureTask> futureTasks=new ArrayList<>();
    private final Scheduler scheduler=new Scheduler();
    private final List<Cycle> cycles=new ArrayList<>();
    private Simulator(){    }
    public static Simulator getInstance() {
        if (simulator == null)
            simulator = new Simulator();
        return simulator;
    }

    public boolean isSimulationDone() {
        return !(!simulator.noFutureTasks() || !simulator.checkAllProcessorsDone());
    }
    public void readExcel(String path){
        checkFileType(path);
        if(checkFileType(path)) {
            ExcelReader ExcelReader = new ExcelReader();
            ExcelReader.readMyExcel(path);
        }
        else
            throw new IllegalArgumentException();
    }

    private boolean checkFileType(String path) {
        String regex = "([^\\s]+(\\.(?i)(xlsx))$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(path);
        return matcher.matches();
    }

    public void writeToExcel(String path){
        if(checkFileType(path)) {
            ExcelWriter excelWriter = new ExcelWriter();
            excelWriter.writeToExcel(path);
        }
        else
            throw new IllegalArgumentException();
    }
    public void simulation() {
        createCycle();
        processorsWorking();
        createTask();
        scheduler.scheduling(cycles.get(cycles.size()-1));
    }
    public void addTask(int creationTime,boolean isHighPriority,int requestedTime) {
        futureTasks.add(new FutureTask(creationTime,isHighPriority,requestedTime));
        sortByCreationTime();
    }
    public void deleteTask(int numberOfTasks) {
        for(int i=0;i<numberOfTasks;i++) {
            if(!futureTasks.isEmpty())
                futureTasks.remove(0);
            else
                throw new NoSuchElementException();
        }
    }
    private void sortByCreationTime() {
        futureTasks.sort(new SortTasksByCreationTime());
    }
    public boolean noFutureTasks(){
        return futureTasks.isEmpty();
    }
    public void createProcessors(int numberOfProcessors) {
        ProcessorsFactory processorsFactory=new ProcessorsFactory();
        List<IProcessor> processors=new ArrayList<>(numberOfProcessors);
        for(int i=0;i<numberOfProcessors;i++)
            processors.add(processorsFactory.createProcessor());
        scheduler.getProcessors(processors);
    }

    private void createCycle(){
        CyclesFactory cyclesFactory=new CyclesFactory();
        cycles.add(cyclesFactory.createCycle());
    }

    private void createTask(){
        TaskFactory taskFactory=new TaskFactory();
        int i=0;
        Cycle cycle=cycles.get(cycles.size()-1);
        while(i!=futureTasks.size()&&futureTasks.get(i).getCreationTime()==cycles.get(cycles.size()-1).getID()) {
            String priority=futureTasks.get(i).isHighPriority()?"high":"low";
            int requestedTime=futureTasks.get(i).getRequestedTime();
            scheduler.addTaskToQueue(taskFactory.taskCreator(priority,cycle,requestedTime));
            i++;
        }
        deleteTask(i);
    }
    public boolean checkAllProcessorsDone() {
        List<IProcessor> processors=scheduler.getProcessors();
        for (IProcessor processor : processors)
            if (processor.isBusy())
                return false;
        return true;
    }

    private void processorsWorking()
    {
        List<IProcessor> processors=scheduler.getProcessors();
        for (IProcessor processor : processors)
            if (processor.isBusy())
                processor.getAttachedTask().decrementRequestedTime();
    }

    @Override
    public String toString() {
        StringBuilder result= new StringBuilder();
        List<Task> completedTasks=scheduler.getCompletedTasks();
        for (Task completedTask : completedTasks) {
            result.append(completedTask.toString())
                    .append("completed at cycle")
                    .append(completedTask.getCompletionCycle().toString()).append("\n");
        }
        return result.toString();
    }

    public List<Task> Output() {
        return scheduler.getCompletedTasks();
    }
}
