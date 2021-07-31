package com.tfushimi.options;

import com.tfushimi.payoffs.Payoff;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EuropeanOptionTest {
    Payoff payoff = Mockito.mock(Payoff.class);

    /**
     * make sure that intermediateValue() always returns discountedFutureValue
     */
    @Test
    void intermediateValue() {
        EuropeanOption option = new EuropeanOption(payoff, 1.0);

        double[] discountedFutureValues = {100, 110, 120};

        for (double discountedFutureValue : discountedFutureValues) {
            assertEquals(
                    discountedFutureValue,
                    option.intermediateValue(100.0, 1.0, discountedFutureValue)
            );
        }
    }
}