package com.krzysztof.pawlak.calculators;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatrixCalculator {

    public Vector<BigDecimal> multiply(double[][] matrix, Vector<BigDecimal> vector) {
        return Arrays.stream(matrix).map(row ->
                IntStream.range(0, row.length)
                        .mapToObj(indexColumn -> BigDecimal.valueOf(row[indexColumn]).multiply(vector.get(indexColumn)))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .collect(Collectors.toCollection(Vector::new));
    }

    public BigDecimal[][] add(BigDecimal[][] matrix, BigDecimal[][] matrix2) {
        return IntStream.range(0, matrix.length)
                .mapToObj(rowIndex -> IntStream.range(0, matrix[rowIndex].length)
                        .mapToObj(columnIndex -> matrix[rowIndex][columnIndex].add(matrix2[rowIndex][columnIndex]))
                        .toArray(BigDecimal[]::new))
                .toArray(BigDecimal[][]::new);
    }

    public BigDecimal[][] subtract(BigDecimal[][] matrix, BigDecimal[][] matrix2) {
        return IntStream.range(0, matrix.length)
                .mapToObj(rowIndex -> IntStream.range(0, matrix[rowIndex].length)
                        .mapToObj(columnIndex -> matrix[rowIndex][columnIndex].subtract(matrix2[rowIndex][columnIndex]))
                        .toArray(BigDecimal[]::new))
                .toArray(BigDecimal[][]::new);
    }

    public BigDecimal[][] multiply(BigDecimal[][] matrix, BigDecimal[][] matrix2) {
        return Arrays.stream(matrix).map(row ->
                IntStream.range(0, row.length)
                        .mapToObj(columnIndexMatrix1 -> IntStream.range(0, matrix2.length)
                                .mapToObj(rowIndexMatrix2 -> row[rowIndexMatrix2].multiply(matrix2[rowIndexMatrix2][columnIndexMatrix1]))
                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                        .toArray(BigDecimal[]::new))
                .toArray(BigDecimal[][]::new);
    }
}