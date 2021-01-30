package com.krzysztof.pawlak.calculators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
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
    void multiplyMatrixAndVectorIntegers() {
        double[][] matrix = {{3, 4}, {5, 5}};
        var vector = new Vector(List.of(BigDecimal.valueOf(3), BigDecimal.valueOf(4)));
        var expected = new Vector(List.of(BigDecimal.valueOf(25).setScale(1), BigDecimal.valueOf(35).setScale(1)));
        assertEquals(expected, matrixCalculator.multiply(matrix, vector));
    }

    @Test
    void addIntegers() {
        BigDecimal[][] matrix1 = {{BigDecimal.valueOf(4), BigDecimal.valueOf(8)}, {BigDecimal.valueOf(3), BigDecimal.valueOf(7)}};
        BigDecimal[][] matrix2 = {{BigDecimal.valueOf(1), BigDecimal.valueOf(0)}, {BigDecimal.valueOf(5), BigDecimal.valueOf(2)}};
        BigDecimal[][] expected = {{BigDecimal.valueOf(5), BigDecimal.valueOf(8)}, {BigDecimal.valueOf(8), BigDecimal.valueOf(9)}};
        BigDecimal[][] result = matrixCalculator.add(matrix1, matrix2);
        assertTrue(Arrays.equals(expected[0], result[0]));
        assertTrue(Arrays.equals(expected[1], result[1]));
    }

    @Test
    void subtractIntegers() {
        BigDecimal[][] matrix1 = {{BigDecimal.valueOf(1), BigDecimal.valueOf(2)}, {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}};
        BigDecimal[][] matrix2 = {{BigDecimal.valueOf(1), BigDecimal.valueOf(3)}, {BigDecimal.valueOf(5), BigDecimal.valueOf(5)}};
        BigDecimal[][] expected = {{BigDecimal.valueOf(0), BigDecimal.valueOf(-1)}, {BigDecimal.valueOf(-2), BigDecimal.valueOf(-1)}};
        BigDecimal[][] result = matrixCalculator.subtract(matrix1, matrix2);
        assertTrue(Arrays.equals(expected[0], result[0]));
        assertTrue(Arrays.equals(expected[1], result[1]));
    }

    @Test
    void multiplyMatrixAndMatrixIntegers() {
        BigDecimal[][] matrix1 = {{BigDecimal.valueOf(1), BigDecimal.valueOf(2)}, {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}};
        BigDecimal[][] matrix2 = {{BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, {BigDecimal.valueOf(0), BigDecimal.valueOf(7)}};
        BigDecimal[][] expected = {{BigDecimal.valueOf(5), BigDecimal.valueOf(20)}, {BigDecimal.valueOf(15), BigDecimal.valueOf(46)}};
        BigDecimal[][] result = matrixCalculator.multiply(matrix1, matrix2);
        System.out.println(Arrays.deepToString(result));
        assertTrue(Arrays.equals(expected[0], result[0]));
        assertTrue(Arrays.equals(expected[1], result[1]));
    }
}