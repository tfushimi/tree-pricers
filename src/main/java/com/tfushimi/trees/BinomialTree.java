package com.tfushimi.trees;

import java.util.Vector;

public abstract class BinomialTree {
    int steps;
    double maturity;
    Vector<Double> times;

    public BinomialTree(int steps, double maturity) {
        Vector<Double> temp = new Vector<>(steps+1);
        for (int i = 0; i < steps + 1; i++) {
            temp.add(i, i * maturity / steps);
        }
        this.steps = steps;
        this.maturity = maturity;
        this.times = temp;
    }

    public BinomialTree(int steps, double maturity, Vector<Double> timeSteps) {
        this.steps = steps;
        this.maturity = maturity;
        this.times = timeSteps;
    }

    public int getSteps() {
        return steps;
    }

    public double getMaturity() {
        return maturity;
    }

    public double getTime(int timeStep) {
        return times.get(timeStep);
    }

    public abstract Node get(int timeStep, int nodeLocation);
}