package com.tfushimi.pricers;

import com.tfushimi.discountCurves.DiscountCurve;
import com.tfushimi.options.Option;
import com.tfushimi.trees.BinomialTree;
import com.tfushimi.trees.BinomialTreeImpl;
import com.tfushimi.trees.Node;
import com.tfushimi.volatility.Volatility;


/**
 * CRRPricer with constant volatility and riskFreeRate
 */
public class CRRPricer extends BinomialTreePricer {
    double spotPrice;
    Volatility volatility;
    protected BinomialTree tree;


    public CRRPricer(double spotPrice,
                     double maturity,
                     int steps,
                     Volatility volatility,
                     DiscountCurve discountCurve) {
        super(discountCurve);
        this.tree = new BinomialTreeImpl(steps, maturity);
        this.spotPrice = spotPrice;
        this.volatility = volatility;
    }

    public CRRPricer(double spotPrice,
                     double[] times,
                     Volatility volatility,
                     DiscountCurve discountCurve) {
        super(discountCurve);
        this.tree = new BinomialTreeImpl(times);
        this.spotPrice = spotPrice;
        this.volatility = volatility;
    }

    public CRRPricer(double spotPrice,
                     Volatility volatility,
                     DiscountCurve discountCurve) {
        super(discountCurve);
        this.spotPrice = spotPrice;
        this.volatility = volatility;
    }

    @Override
    public void buildTree() {
        for (int nodeLocation = 0; nodeLocation < tree.getNumSteps() + 1; nodeLocation++) {
            for (int timeStep = nodeLocation; timeStep < tree.getNumSteps() + 1; timeStep++) {
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


    @Override
    public double price(Option option) throws Exception {
        if (tree != null) {
            buildTree();
        }

        if (option.getMaturity() != tree.getMaturity()) {
            throw new Exception("");
        }

        // calculate terminal price
        for (int i = 0; i < tree.getNumSteps() + 1; i++) {
            Node node = tree.get(tree.getNumSteps(), i);
            node.setOptionValue(option.finalValue(node.getSpotPrice()));
        }

        // backward
        int numNodes = tree.getNumSteps();
        for (int i = 1; i < tree.getNumSteps() + 1; i++) {
            int currentTimeStep = tree.getNumSteps() - i;
            // calculate option value of nodes at current time step
            for (int nodeLocation = 0; nodeLocation < numNodes; nodeLocation++) {
                Node node = tree.get(currentTimeStep, nodeLocation);
                Node upNode = tree.get(currentTimeStep+1, nodeLocation);
                Node downNode = tree.get(currentTimeStep+1, nodeLocation+1);

                double discountedFutureValue = getDiscountedExpectation(node, upNode, downNode);
                node.setOptionValue(option.intermediateValue(node.getSpotPrice(), node.getTime(), discountedFutureValue));
            }
            numNodes--;
        }

        return tree.get(0, 0).getOptionValue();
    }

    private double getExpectation(Node upNode, Node downNode, double riskNeutralProb) {
        return riskNeutralProb * upNode.getOptionValue() + (1 - riskNeutralProb) * downNode.getOptionValue();
    }

    private double getDiscountedExpectation(Node node, Node upNode, Node downNode) {
        double discountFactor = discountCurve.get(node.getTime(), node.getEndTime());
        return discountFactor * getExpectation(upNode, downNode, node.getRiskNeutralProb());
    }
}
