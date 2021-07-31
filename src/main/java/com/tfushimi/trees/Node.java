package com.tfushimi.trees;

/**
 * Class to represent a Node in BinomialTree.
 * Node contains information at current state
 * spotPrice: current stock price
 * optionValue:
 */
public class Node {
    double spotPrice;
    double optionValue;
    double time;
    double timeDelta;
    double upReturn;
    double downReturn;
    double riskNeutralProb;

    public Node(double time, double timeDelta) {
        this.time = time;
        this.timeDelta = timeDelta;
    }

    public double getSpotPrice() {
        return spotPrice;
    }

    public void setSpotPrice(double spotPrice) {
        this.spotPrice = spotPrice;
    }

    public void setOptionValue(double optionValue) {
        this.optionValue = optionValue;
    }

    public double getOptionValue() {
        return optionValue;
    }

    public void setRiskNeutralProb(double riskNeutralProb) {
        this.riskNeutralProb = riskNeutralProb;
    }

    public double getRiskNeutralProb() {
        return riskNeutralProb;
    }

    public double getTime() {
        return time;
    }

    public double getTimeDelta() {
        return timeDelta;
    }

    public double getEndTime() {
        return time + timeDelta;
    }

    public double getUpReturn() {
        return upReturn;
    }

    public void setUpReturn(double upReturn) {
        this.upReturn = upReturn;
    }

    public double getDownReturn() {
        return downReturn;
    }

    public void setDownReturn(double downReturn) {
        this.downReturn = downReturn;
    }
}
