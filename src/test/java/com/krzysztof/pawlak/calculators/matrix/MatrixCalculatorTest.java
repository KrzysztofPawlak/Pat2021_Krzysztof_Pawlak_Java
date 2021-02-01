package com.krzysztof.pawlak.calculators.matrix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatrixCalculatorTest {

    private MatrixByMatrixCalculator matrixByMatrixCalculator;
    private MatrixByVectorCalculator matrixByVectorCalculator;
    private MatrixByNumberCalculator matrixByNumberCalculator;

    @BeforeEach
    void setUp() {
        matrixByMatrixCalculator = new MatrixByMatrixCalculator();
        matrixByVectorCalculator = new MatrixByVectorCalculator();
        matrixByNumberCalculator = new MatrixByNumberCalculator();
    }

    @Test
    void multiplyMatrixAndVectorIntegers() {
        double[][] matrix = {{3, 4}, {5, 5}};
        var vector = new Vector(List.of(BigDecimal.valueOf(3), BigDecimal.valueOf(4)));
        var expected = new Vector(List.of(BigDecimal.valueOf(25).setScale(1), BigDecimal.valueOf(35).setScale(1)));
        assertEquals(expected, matrixByVectorCalculator.multiply(matrix, vector));
    }

    @Test
    void addIntegers() {
        BigDecimal[][] matrix1 = {{BigDecimal.valueOf(4), BigDecimal.valueOf(8)}, {BigDecimal.valueOf(3), BigDecimal.valueOf(7)}};
        BigDecimal[][] matrix2 = {{BigDecimal.valueOf(1), BigDecimal.valueOf(0)}, {BigDecimal.valueOf(5), BigDecimal.valueOf(2)}};
        BigDecimal[][] expected = {{BigDecimal.valueOf(5), BigDecimal.valueOf(8)}, {BigDecimal.valueOf(8), BigDecimal.valueOf(9)}};
        BigDecimal[][] result = matrixByMatrixCalculator.add(matrix1, matrix2);
        assertTrue(Arrays.equals(expected[0], result[0]));
        assertTrue(Arrays.equals(expected[1], result[1]));
    }

    @Test
    void subtractIntegers() {
        BigDecimal[][] matrix1 = {{BigDecimal.valueOf(1), BigDecimal.valueOf(2)}, {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}};
        BigDecimal[][] matrix2 = {{BigDecimal.valueOf(1), BigDecimal.valueOf(3)}, {BigDecimal.valueOf(5), BigDecimal.valueOf(5)}};
        BigDecimal[][] expected = {{BigDecimal.valueOf(0), BigDecimal.valueOf(-1)}, {BigDecimal.valueOf(-2), BigDecimal.valueOf(-1)}};
        BigDecimal[][] result = matrixByMatrixCalculator.subtract(matrix1, matrix2);
        assertTrue(Arrays.equals(expected[0], result[0]));
        assertTrue(Arrays.equals(expected[1], result[1]));
    }

    @Test
    void multiplyMatrixAndMatrixIntegers() {
        BigDecimal[][] matrix1 = {{BigDecimal.valueOf(1), BigDecimal.valueOf(2)}, {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}};
        BigDecimal[][] matrix2 = {{BigDecimal.valueOf(5), BigDecimal.valueOf(6)}, {BigDecimal.valueOf(0), BigDecimal.valueOf(7)}};
        BigDecimal[][] expected = {{BigDecimal.valueOf(5), BigDecimal.valueOf(20)}, {BigDecimal.valueOf(15), BigDecimal.valueOf(46)}};
        BigDecimal[][] result = matrixByMatrixCalculator.multiply(matrix1, matrix2);
        assertTrue(Arrays.equals(expected[0], result[0]));
        assertTrue(Arrays.equals(expected[1], result[1]));
    }

    @Test
    void multiplyMatrixAndNumberIntegers() {
        BigDecimal[][] matrix = {{BigDecimal.valueOf(1), BigDecimal.valueOf(2)}, {BigDecimal.valueOf(3), BigDecimal.valueOf(4)}};
        BigDecimal number = BigDecimal.valueOf(3);
        BigDecimal[][] expected = {{BigDecimal.valueOf(3), BigDecimal.valueOf(6)}, {BigDecimal.valueOf(9), BigDecimal.valueOf(12)}};
        BigDecimal[][] result = matrixByNumberCalculator.multiply(matrix, number);
        assertTrue(Arrays.equals(expected[0], result[0]));
        assertTrue(Arrays.equals(expected[1], result[1]));
    }
}