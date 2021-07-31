package com.tfushimi;

import com.tfushimi.discountCurves.ConstantDiscountCurve;
import com.tfushimi.options.AmericanOption;
import com.tfushimi.options.EuropeanOption;
import com.tfushimi.payoffs.Call;
import com.tfushimi.payoffs.Put;
import com.tfushimi.pricers.CRRPricer;
import com.tfushimi.volatility.ConstantVolatility;

public class Main {

    public static void main(String[] args) {
        var call = new Call(100.0);
        var put = new Put(100.0);

        var callOption = new EuropeanOption(call, 1.0);
        var putOption = new EuropeanOption(put,1.0);

        var americanCallOption = new AmericanOption(call, 1.0);
        var americanPutOption = new AmericanOption(put, 1.0);

        var discountCurve = new ConstantDiscountCurve(0.01);
        var volatility = new ConstantVolatility(0.15);
        var pricer = new CRRPricer(100.0, 1.0, 252, volatility, discountCurve);

        try {
            System.out.println("European Call=" + pricer.price(callOption));
            System.out.println("American Call=" + pricer.price(americanCallOption));
            System.out.println("European Put=" + pricer.price(putOption));
            System.out.println("American Put=" + pricer.price(americanPutOption));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
