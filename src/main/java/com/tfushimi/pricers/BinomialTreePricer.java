package com.tfushimi.pricers;

import com.tfushimi.discountCurves.DiscountCurve;
import com.tfushimi.options.Option;

public abstract class BinomialTreePricer {
    protected DiscountCurve discountCurve;

    public BinomialTreePricer(DiscountCurve discountCurve) {
        this.discountCurve = discountCurve;
    }

    public abstract void buildTree();

    public abstract double price(Option option) throws Exception;
}
