package com.tfushimi.trees;

public abstract class BinomialTree {
    private final int numSteps;
    private final double maturity;
    private final double[] times;

    public BinomialTree(int numSteps, double maturity) {
        this.times = new double[numSteps+1];
        for (int i = 0; i < numSteps + 1; i++) {
            times[i] = i * maturity / numSteps;
        }
        this.numSteps = numSteps;
        this.maturity = maturity;
        initialize();
    }

    public BinomialTree(double[] times) {
        this.numSteps = times.length - 1;
        this.maturity = times[times.length - 1];
        this.times = times;
        initialize();
    }

    public int getNumSteps() {
        return numSteps;
    }

    public double getMaturity() {
        return maturity;
    }

    public double getTime(int index) {
        return times[index];
    }

    protected abstract void initialize();

    public abstract Node get(int timeStep, int nodeLocation);
}