package com.tfushimi.trees;

import java.util.Vector;

public abstract class BinomialTree {
    int numSteps;
    double maturity;
    Vector<Double> times;

    public BinomialTree(int numSteps, double maturity) {
        Vector<Double> temp = new Vector<>(numSteps +1);
        for (int i = 0; i < numSteps + 1; i++) {
            temp.add(i, i * maturity / numSteps);
        }
        this.numSteps = numSteps;
        this.maturity = maturity;
        this.times = temp;
        initialize();
    }

    public BinomialTree(Vector<Double> times) {
        this.numSteps = times.size() - 1;
        this.maturity = times.lastElement();
        this.times = times;
        initialize();
    }

    public int getNumSteps() {
        return numSteps;
    }

    public double getMaturity() {
        return maturity;
    }

    protected abstract void initialize();

    public abstract Node get(int timeStep, int nodeLocation);
}