package com.krzysztof.pawlak.calculators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorCalculatorTest {

    private VectorCalculator vectorCalculator;

    @BeforeEach
    void setUp() {
        vectorCalculator = new VectorCalculator();
    }

    @Test
    void multiply() {
        var vector = new Vector(List.of(1.2, 2.1));
        var expected = new Vector(List.of(2.4, 4.2));
        assertEquals(expected, vectorCalculator.multiply(vector, 2));
    }

    @Test
    void add() {
        var vector = new Vector(List.of(1.2, 2.1));
        var vector2 = new Vector(List.of(2.3, 1.6));
        var expected = new Vector(List.of(3.5, 3.7));
        assertEquals(expected, vectorCalculator.add(vector, vector2));
    }

    @Test
    void subtract() {
        var vector = new Vector(List.of(BigDecimal.valueOf(8.2), BigDecimal.valueOf(6.1)));
        var vector2 = new Vector(List.of(BigDecimal.valueOf(4.3), BigDecimal.valueOf(2.6)));
        var expected = new Vector(List.of(BigDecimal.valueOf(3.9), BigDecimal.valueOf(3.5)));
        assertEquals(expected, vectorCalculator.subtract(vector, vector2));
    }
}