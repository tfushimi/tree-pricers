package com.tfushimi.payoffs;

public interface Payoff {
    double calculate(double spotPrice);
}