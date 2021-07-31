package com.tfushimi.payoffs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PutTest {
    @Test
    void calculate() {
        var put = new Put(100.0);

        // ITM
        assertEquals(put.calculate(90.0), 10.0);

        // ATM
        assertEquals(put.calculate(100.0), 0.0);

        // OTM
        assertEquals(put.calculate(110.0), 0.0);
    }
}