package com.krzysztof.pawlak.calculators.matrix;

import com.krzysztof.pawlak.calculators.Calculator;
import com.krzysztof.pawlak.models.ValueContainer;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MatrixByMatrixCalculator implements Calculator {

    private enum Operations {
        ADD(1),
        SUBTRACT(2),
        MULTIPLY(3);

        private static Map map = new HashMap<>();
        private final int value;

        Operations(int value) {
            this.value = value;
        }

        static {
            for (Operations operation : Operations.values()) {
                map.put(operation.value, operation);
            }
        }

        static Operations valueOf(int operation) {
            return (Operations) map.get(operation);
        }
    }

    public BigDecimal[][] calculate(Deque<ValueContainer> deque, int operation) {
        final var selectedOperation = Operations.valueOf(operation);
        final var value = deque.peekFirst();
        final var value2 = deque.peekLast();
        switch (selectedOperation) {
            case ADD:
                return add((BigDecimal[][]) value.getValue(), (BigDecimal[][]) value2.getValue());
            case SUBTRACT:
                return subtract((BigDecimal[][]) value.getValue(), (BigDecimal[][]) value2.getValue());
            case MULTIPLY:
                return multiply((BigDecimal[][]) value.getValue(), (BigDecimal[][]) value2.getValue());
            default:
                throw new UnsupportedOperationException();
        }
    }

    public BigDecimal[][] add(BigDecimal[][] matrix, BigDecimal[][] matrix2) {
        if (matrix.length != matrix2.length || matrix[0].length != matrix2[0].length) {
            throw new IllegalArgumentException("Sorry, it's not possible to add matrices with different rows and columns length.");
        }
        return IntStream.range(0, matrix.length)
                .mapToObj(rowIndex -> IntStream.range(0, matrix[rowIndex].length)
                        .mapToObj(columnIndex -> matrix[rowIndex][columnIndex].add(matrix2[rowIndex][columnIndex]))
                        .toArray(BigDecimal[]::new))
                .toArray(BigDecimal[][]::new);
    }

    public BigDecimal[][] subtract(BigDecimal[][] matrix, BigDecimal[][] matrix2) {
        if (matrix.length != matrix2.length || matrix[0].length != matrix2[0].length) {
            throw new IllegalArgumentException("Sorry, it's not possible to subtract matrices with different rows and columns length.");
        }
        return IntStream.range(0, matrix.length)
                .mapToObj(rowIndex -> IntStream.range(0, matrix[rowIndex].length)
                        .mapToObj(columnIndex -> matrix[rowIndex][columnIndex].subtract(matrix2[rowIndex][columnIndex]))
                        .toArray(BigDecimal[]::new))
                .toArray(BigDecimal[][]::new);
    }

    public BigDecimal[][] multiply(BigDecimal[][] matrix, BigDecimal[][] matrix2) {
        if (matrix.length != matrix2[0].length || matrix[0].length != matrix2.length) {
            throw new IllegalArgumentException("Sorry, it's not possible to multiply matrices with this rows and columns length.");
        }
        return Arrays.stream(matrix).map(row ->
                IntStream.range(0, matrix2[0].length)
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