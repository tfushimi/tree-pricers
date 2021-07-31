package com.tfushimi.options;

import com.tfushimi.payoffs.Payoff;

public class EuropeanOption extends Option {
    public EuropeanOption(Payoff payoff, double maturity) {
        super(payoff, maturity);
    }

    @Override
    public double intermediateValue(double spotPrice, double time, double discountedFutureValue) {
        return discountedFutureValue;
    }
}
