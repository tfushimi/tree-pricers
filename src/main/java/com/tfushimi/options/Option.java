package com.tfushimi.options;

import com.tfushimi.payoffs.Payoff;

public abstract class Option {
    private final Payoff payoff;
    private final double maturity;

    public Option(Payoff payoff, double maturity) {
        this.payoff = payoff;
        this.maturity = maturity;
    }

    public double finalValue(double spotPrice) {
        return payoff.calculate(spotPrice);
    }

    public double getMaturity() {
        return maturity;
    }

    public abstract double intermediateValue(double spotPrice, double time, double discountedFutureValue);
}
