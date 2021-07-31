package com.tfushimi.volatility;

public class ConstantVolatility implements Volatility {
    final double volatility;

    public ConstantVolatility(double volatility) {
        this.volatility = volatility;
    }

    @Override
    public double get(double startTime, double endTime) {
        return volatility * Math.sqrt(endTime - startTime);
    }
}
