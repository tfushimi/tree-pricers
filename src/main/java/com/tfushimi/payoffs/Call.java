package com.tfushimi.payoffs;

public class Call implements Payoff {
    private final double strike;

    public Call(double strike) {
        this.strike = strike;
    }

    @Override
    public double calculate(double spotPrice) {
        return Math.max(spotPrice - strike, 0.0);
    }
}
