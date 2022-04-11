package com.tfushimi.trees;

public class BinomialTreeImpl extends BinomialTree {
    private Node[][] tree;

    public BinomialTreeImpl(int steps, double maturity) {
        super(steps, maturity);
    }

    public BinomialTreeImpl(double[] times) {
        super(times);
    }

    @Override
    protected void initialize() {
        // reshape the tree so that there are step + 1 time steps
        tree = new Node[getNumSteps()+1][];

        // forward induction
        // number of nodes at each time step
        int numNodes = 1;
        for (int i = 0; i < getNumSteps() + 1; i++) {
            tree[i] = new Node[numNodes];
            for (int j = 0; j < numNodes; j++) {
                double timeDelta = 0;
                if (i < getNumSteps()) {
                    timeDelta = getTime(i + 1) - getTime(i);

                }
                tree[i][j] = new Node(getTime(i), timeDelta);
            }
            numNodes++;
        }
    }

    @Override
    public Node get(int i, int j) {

        return tree[i][j];
    }
}
