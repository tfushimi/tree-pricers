package com.tfushimi.payoffs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallTest {
    @Test
    void calculate() {
        var call = new Call(100.0);

        // OTM
        assertEquals(call.calculate(90.0), 0.0);

        // ATM
        assertEquals(call.calculate(100.0), 0.0);

        // ITM
        assertEquals(call.calculate(110.0), 10.0);
    }
}