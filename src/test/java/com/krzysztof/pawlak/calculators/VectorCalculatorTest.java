package com.krzysztof.pawlak.calculators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Vector<Double> vector = new Vector(List.of(1.2, 2.1));
        Vector<Double> expected = new Vector(List.of(2.4, 4.2));
        assertEquals(expected, vectorCalculator.multiply(vector, 2));
    }

    @Test
    void add() {
        Vector<Double> vector = new Vector(List.of(1.2, 2.1));
        Vector<Double> vector2 = new Vector(List.of(2.3, 1.6));
        Vector<Double> expected = new Vector(List.of(3.5, 3.7));
        assertEquals(expected, vectorCalculator.add(vector, vector2));
    }

    @Test
    void subtract() {
        Vector<Double> vector = new Vector(List.of(8.2, 6.1));
        Vector<Double> vector2 = new Vector(List.of(4.3, 2.6));
        Vector<Double> expected = new Vector(List.of(3.9, 3.5));
        assertEquals(expected, vectorCalculator.subtract(vector, vector2));
    }
}