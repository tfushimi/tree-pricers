package com.tfushimi.pricers;

import com.tfushimi.discountCurves.DiscountCurve;
import com.tfushimi.trees.BinomialTree;
import com.tfushimi.trees.Node;
import com.tfushimi.trees.BinomialTreeImpl;
import com.tfushimi.volatility.Volatility;

import java.util.Vector;

/**
 * CRRPricer with constant volatility and riskFreeRate
 */
public class CRRPricer extends BinomialTreePricer {
    double spotPrice;
    Volatility volatility;

    public CRRPricer(double spotPrice,
                     double maturity,
                     int steps,
                     Volatility volatility,
                     DiscountCurve discountCurve) {
        super(new BinomialTreeImpl(steps, maturity), discountCurve);
        this.spotPrice = spotPrice;
        this.volatility = volatility;
    }

    public CRRPricer(double spotPrice,
                     Vector<Double> times,
                     Volatility volatility,
                     DiscountCurve discountCurve) {
        super(new BinomialTreeImpl(times), discountCurve);
        this.spotPrice = spotPrice;
        this.volatility = volatility;
    }

    public CRRPricer(double spotPrice,
                     Volatility volatility,
                     DiscountCurve discountCurve,
                     BinomialTree tree) {
        super(tree, discountCurve);
        this.spotPrice = spotPrice;
        this.volatility = volatility;
    }

    @Override
    public void buildTree() {
        for (int nodeLocation = 0; nodeLocation < tree.getSteps() + 1; nodeLocation++) {
            for (int timeStep = nodeLocation; timeStep < tree.getSteps() + 1; timeStep++) {
                Node node = tree.get(timeStep, nodeLocation);
                // set spot price at current node
                if (timeStep == nodeLocation) {
                    if (timeStep == 0) {
                        // root of tree
                        node.setSpotPrice(spotPrice);
                    } else {
                        // for diagonal nodes, use previous diagonal node times downReturn
                        Node prevNode = tree.get(timeStep-1, nodeLocation-1);
                        node.setSpotPrice(prevNode.getSpotPrice() * prevNode.getDownReturn());
                    }
                } else {
                    // for off-diagonal nodes, use previous lower node times upReturn
                    Node prevNode = tree.get(timeStep-1, nodeLocation);
                    node.setSpotPrice(prevNode.getSpotPrice() * prevNode.getUpReturn());
                }
                double up = Math.exp(volatility.get(node.getTime(), node.getEndTime()));
                double down = 1 / up;
                double discountFactor = discountCurve.get(node.getTime(), node.getEndTime());

                // set other parameters
                node.setUpReturn(up);
                node.setDownReturn(down);
                node.setRiskNeutralProb((1 / discountFactor - down) / (up - down));
            }
        }
    }
}
