package com.tfushimi.options;

import com.tfushimi.payoffs.Payoff;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OptionTest {
    double maturity = 1.0;
    Payoff payoff = Mockito.mock(Payoff.class);
    Option option = Mockito.mock(
            Option.class,
            Mockito.withSettings()
                    .useConstructor(payoff, maturity)
                    .defaultAnswer(Mockito.CALLS_REAL_METHODS)
    );

    @Test
    void finalValue() {
        double spotPrice = 100.0;
        double finalValue = 10.0;

        Mockito.when(payoff.calculate(spotPrice)).thenReturn(finalValue);
        assertEquals(finalValue, option.finalValue(spotPrice));
        Mockito.verify(payoff).calculate(spotPrice);
    }

    @Test
    void getMaturity() {
        assertEquals(maturity, option.getMaturity());
    }
}