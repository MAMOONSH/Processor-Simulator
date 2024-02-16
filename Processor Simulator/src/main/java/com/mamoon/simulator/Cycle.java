package com.mamoon.simulator;

public class Cycle {
    private final int ID;
    public Cycle(int id) {
        ID = id;
    }
    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Cycle{" +
                "ID=" + ID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cycle cycle = (Cycle) o;
        return ID == cycle.ID;
    }
}
