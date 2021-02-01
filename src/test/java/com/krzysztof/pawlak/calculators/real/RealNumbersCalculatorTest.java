package com.krzysztof.pawlak.calculators.real;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RealNumbersCalculatorTest {

    private RealNumbersCalculator realNumbersCalculator;

    @BeforeEach
    void setUp() {
        realNumbersCalculator = new RealNumbersCalculator();
    }

    @Test
    void add() {
        assertEquals(BigDecimal.valueOf(5), realNumbersCalculator.add(BigDecimal.valueOf(2), BigDecimal.valueOf(3)));
    }

    @Test
    void subtract() {
        assertEquals(BigDecimal.valueOf(80), realNumbersCalculator.subtract(BigDecimal.valueOf(99), BigDecimal.valueOf(19)));
    }

    @Test
    void multiply() {
        assertEquals(BigDecimal.valueOf(66), realNumbersCalculator.multiply(BigDecimal.valueOf(6), BigDecimal.valueOf(11)));
    }

    @Test
    void divide() {
        assertEquals(BigDecimal.valueOf(5), realNumbersCalculator.divide(BigDecimal.valueOf(55), BigDecimal.valueOf(11)));
    }

    @Test
    void exp() {
        assertEquals(BigDecimal.valueOf(1331), realNumbersCalculator.exp(BigDecimal.valueOf(11), 3));
    }

    @Test
    void sqrt() {
        assertEquals(BigDecimal.valueOf(15), realNumbersCalculator.sqrt(BigDecimal.valueOf(225)));
    }
}