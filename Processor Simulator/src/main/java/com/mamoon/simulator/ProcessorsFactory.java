package com.mamoon.simulator;

public class ProcessorsFactory {
    private static int ID =0;
    public IProcessor createProcessor()
    {
        ID++;
        /*List<IProcessor> processors= new ArrayList<>();
        for(int i=1;i<=numberOfProcessors;i++)
        {
            processors.add(new Processor(i));
        }*/
        return new Processor(ID);
    }
}
