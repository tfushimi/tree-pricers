package com.tfushimi.trees;

import java.util.Vector;

public class BinomialTreeImpl extends BinomialTree {
    private Vector<Vector<Node>> tree;

    public BinomialTreeImpl(int steps, double maturity) {
        super(steps, maturity);
    }

    public BinomialTreeImpl(Vector<Double> times) {
        super(times);
    }

    @Override
    protected void initialize() {
        // reshape the tree so that there are step + 1 time steps
        tree = new Vector<>(steps + 1);

        // forward induction
        // number of nodes at each time step
        int numNodes = 1;
        for (int i = 0; i < steps + 1; i++) {
            Vector<Node> nodes = new Vector<>(numNodes);
            for (int j = 0; j < numNodes; j++) {
                double timeDelta = 0;
                if (i < steps) {
                    timeDelta = times.get(i + 1) - times.get(i);
                }
                nodes.add(j, new Node(times.get(i), timeDelta));
            }
            tree.add(i, nodes);
            numNodes++;
        }
    }

    @Override
    public Node get(int i, int j) {
        return tree.get(i).get(j);
    }
}
