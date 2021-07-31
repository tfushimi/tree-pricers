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
        initialize();
    }

    public BinomialTree(Vector<Double> times) {
        this.steps = times.size() - 1;
        this.maturity = times.lastElement();
        this.times = times;
        initialize();
    }

    public int getSteps() {
        return steps;
    }

    public double getMaturity() {
        return maturity;
    }

    protected abstract void initialize();

    public abstract Node get(int timeStep, int nodeLocation);
}