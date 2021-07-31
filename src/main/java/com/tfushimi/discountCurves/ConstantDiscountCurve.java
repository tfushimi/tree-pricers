package com.tfushimi.discountCurves;

public class ConstantDiscountCurve implements DiscountCurve {
    final double riskFreeRate;

    public ConstantDiscountCurve(double riskFreeRate) {
        this.riskFreeRate = riskFreeRate;
    }

    @Override
    public double get(double startTime, double endTime) {
        return Math.exp(-riskFreeRate * (endTime - startTime));
    }
}
