package com.krzysztof.pawlak.calculators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class MatrixCalculatorTest {

    private MatrixCalculator matrixCalculator;

    @BeforeEach
    void setUp() {
        matrixCalculator = new MatrixCalculator();
    }

    @Test
    void multiplyIntegers() {
        double[][] matrix = {{3, 4}, {5, 5}};
        var vector = new Vector(List.of(BigDecimal.valueOf(3), BigDecimal.valueOf(4)));
        var expected = new Vector(List.of(BigDecimal.valueOf(25).setScale(1), BigDecimal.valueOf(35).setScale(1)));
        assertEquals(expected, matrixCalculator.multiply(matrix, vector));
    }

    @Test
    void add() {
    }

    @Test
    void subtract() {
    }
}