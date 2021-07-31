package com.tfushimi.options;

import com.tfushimi.payoffs.Payoff;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AmericanOptionTest {

    /**
     * make sure that intermediateValue() calculates max(intrinsicValue, discountedFutureValue)
     */
    @Test
    void intermediateValue() {
        double spotPrice = 100;
        double[] intrinsicValues = {10.0, 5.0, 10.0};
        double[] discountedFutureValues = {5.0, 10.0, 10.0};
        double expectedValue = 10.0;

        for (int i = 0; i < intrinsicValues.length; i++) {
            Payoff payoff = Mockito.mock(Payoff.class);
            AmericanOption option = new AmericanOption(payoff, 1.0);

            when(payoff.calculate(spotPrice)).thenReturn(intrinsicValues[i]);

            assertEquals(expectedValue, option.intermediateValue(spotPrice, 1.0, discountedFutureValues[i]));

            verify(payoff).calculate(spotPrice);
        }
    }
}
