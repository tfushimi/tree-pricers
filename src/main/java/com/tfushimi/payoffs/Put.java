package com.tfushimi.payoffs;

public class Put implements Payoff {
    private final double strike;

    public Put(double strike) {
        this.strike = strike;
    }

    @Override
    public double calculate(double spotPrice) {
        return Math.max(strike - spotPrice, 0.0);
    }
}
