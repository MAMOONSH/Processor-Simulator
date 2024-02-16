package com.mamoon.simulator;

public class CyclesFactory {
    private static int cycleNumber=0;
    public Cycle createCycle()
    {
        return new Cycle(++cycleNumber);
    }
}
