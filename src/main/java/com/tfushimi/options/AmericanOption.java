package com.tfushimi.options;

import com.tfushimi.payoffs.Payoff;

public class AmericanOption extends Option {
    public AmericanOption(Payoff payoff, double maturity) {
        super(payoff, maturity);
    }

    @Override
    public double intermediateValue(double spotPrice, double time, double discountedFutureValue) {
        return Math.max(finalValue(spotPrice), discountedFutureValue);
    }
}
