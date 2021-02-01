package com.krzysztof.pawlak.calculators.matrix;

import com.krzysztof.pawlak.calculators.Suggestive;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MatrixByMatrixCalculator implements Suggestive {

    private enum Operations {
        ADD(0),
        SUBTRACT(1),
        MULTIPLY(2);

        private static Map map = new HashMap<>();
        private final int value;

        Operations(int value) {
            this.value = value;
        }

        static {
            for (Operations operation : Operations.values()) {
                map.put(operation.value, map);
            }
        }

        static Operations valueOf(int operation) {
            return (Operations) map.get(operation);
        }
    }

    public BigDecimal[][] calculate(BigDecimal[][] matrix, BigDecimal[][] matrix2, int operation) {
        var selectedOperation = Operations.valueOf(operation);
        switch (selectedOperation) {
            case ADD:
                return add(matrix, matrix2);
            case SUBTRACT:
                return subtract(matrix, matrix2);
            case MULTIPLY:
                return multiply(matrix, matrix2);
            default:
                throw new UnsupportedOperationException();
        }
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

    @Override
    public List<String> suggest() {
        return Stream.of(Operations.values()).map(Enum::toString).collect(Collectors.toList());
    }
}