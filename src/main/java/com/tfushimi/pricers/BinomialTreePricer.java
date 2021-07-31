package com.tfushimi.pricers;

import com.tfushimi.discountCurves.DiscountCurve;
import com.tfushimi.options.Option;
import com.tfushimi.trees.BinomialTree;
import com.tfushimi.trees.Node;

public abstract class BinomialTreePricer {
    boolean isTreeBuilt = false;
    protected BinomialTree tree;
    protected DiscountCurve discountCurve;

    public BinomialTreePricer(BinomialTree tree, DiscountCurve discountCurve) {
        this.tree = tree;
        this.discountCurve = discountCurve;
    }

    public abstract void buildTree();

    public BinomialTree getTree() {
        return tree;
    }

    private double getExpectation(Node upNode, Node downNode, double riskNeutralProb) {
        return riskNeutralProb * upNode.getOptionValue() + (1 - riskNeutralProb) * downNode.getOptionValue();
    }

    private double getDiscountedExpectation(Node node, Node upNode, Node downNode) {
        double discountFactor = discountCurve.get(node.getTime(), node.getEndTime());
        return discountFactor * getExpectation(upNode, downNode, node.getRiskNeutralProb());
    }

    public double price(Option option) throws Exception {
        if (!isTreeBuilt) {
            isTreeBuilt = true;
            buildTree();
        }

        if (option.getMaturity() != tree.getMaturity()) {
            throw new Exception("");
        }

        // calculate terminal price
        for (int i = 0; i < tree.getSteps() + 1; i++) {
            Node node = tree.get(tree.getSteps(), i);
            node.setOptionValue(option.finalValue(node.getSpotPrice()));
        }

        // backward
        int numNodes = tree.getSteps();
        for (int i = 1; i < tree.getSteps() + 1; i++) {
            int currentTimeStep = tree.getSteps() - i;
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
    }}
