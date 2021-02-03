package com.krzysztof.pawlak.calculators.vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorCalculatorTest {

    private VectorByVectorCalculator vectorByVectorCalculator;
    private VectorByNumberCalculator vectorByNumberCalculator;

    @BeforeEach
    void setUp() {
        vectorByVectorCalculator = new VectorByVectorCalculator();
        vectorByNumberCalculator = new VectorByNumberCalculator();
    }

    @Test
    void multiply() {
        var vector = new Vector(List.of(1.2, 2.1));
        var expected = new Vector(List.of(2.4, 4.2));
        assertEquals(expected, vectorByNumberCalculator.multiply(vector, BigDecimal.valueOf(2)));
    }

    @Test
    void add() {
        var vector = new Vector(List.of(1.2, 2.1));
        var vector2 = new Vector(List.of(2.3, 1.6));
        var expected = new Vector(List.of(3.5, 3.7));
        assertEquals(expected, vectorByVectorCalculator.add(vector, vector2));
    }

    @Test
    void subtract() {
        var vector = new Vector(List.of(BigDecimal.valueOf(8.2), BigDecimal.valueOf(6.1)));
        var vector2 = new Vector(List.of(BigDecimal.valueOf(4.3), BigDecimal.valueOf(2.6)));
        var expected = new Vector(List.of(BigDecimal.valueOf(3.9), BigDecimal.valueOf(3.5)));
        assertEquals(expected, vectorByVectorCalculator.subtract(vector, vector2));
    }
}