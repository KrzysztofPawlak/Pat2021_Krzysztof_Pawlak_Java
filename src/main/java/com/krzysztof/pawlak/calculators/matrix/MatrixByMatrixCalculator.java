package com.krzysztof.pawlak.calculators.matrix;

import com.krzysztof.pawlak.calculators.Calculator;
import com.krzysztof.pawlak.error.CalculationConstrainException;
import com.krzysztof.pawlak.error.CalculationNotImplementedException;
import com.krzysztof.pawlak.models.OperationChar;
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

        private final int value;

        Operations(int value) {
            this.value = value;
        }

        static Operations valueOf(OperationChar operation) {
            return Arrays.stream(Operations.values())
                    .filter(enumOperation -> enumOperation.toString().equals(operation.toString()))
                    .findFirst()
                    .orElseThrow(CalculationNotImplementedException::new);
        }
    }

    @Override
    public BigDecimal[][] calculate(Deque<ValueContainer> deque, OperationChar operation) {
        final var selectedOperation = Operations.valueOf(operation);
        final var value = (BigDecimal[][]) deque.peekFirst().getValue();
        final var value2 = (BigDecimal[][]) deque.peekLast().getValue();
        switch (selectedOperation) {
            case ADD:
                return add(value, value2);
            case SUBTRACT:
                return subtract(value, value2);
            case MULTIPLY:
                return multiply(value, value2);
            default:
                throw new CalculationNotImplementedException();
        }
    }

    public BigDecimal[][] add(BigDecimal[][] matrix, BigDecimal[][] matrix2) {
        if (matrix.length != matrix2.length || matrix[0].length != matrix2[0].length) {
            throw new CalculationConstrainException(
                    "Sorry, it's not possible to add matrices with different rows and columns length.");
        }
        return IntStream.range(0, matrix.length)
                .mapToObj(rowIndex -> IntStream.range(0, matrix[rowIndex].length)
                        .mapToObj(columnIndex -> matrix[rowIndex][columnIndex].add(matrix2[rowIndex][columnIndex]))
                        .toArray(BigDecimal[]::new))
                .toArray(BigDecimal[][]::new);
    }

    public BigDecimal[][] subtract(BigDecimal[][] matrix, BigDecimal[][] matrix2) {
        if (matrix.length != matrix2.length || matrix[0].length != matrix2[0].length) {
            throw new CalculationConstrainException(
                    "Sorry, it's not possible to subtract matrices with different rows and columns length.");
        }
        return IntStream.range(0, matrix.length)
                .mapToObj(rowIndex -> IntStream.range(0, matrix[rowIndex].length)
                        .mapToObj(columnIndex -> matrix[rowIndex][columnIndex].subtract(matrix2[rowIndex][columnIndex]))
                        .toArray(BigDecimal[]::new))
                .toArray(BigDecimal[][]::new);
    }

    public BigDecimal[][] multiply(BigDecimal[][] matrix, BigDecimal[][] matrix2) {
        if (matrix.length != matrix2[0].length || matrix[0].length != matrix2.length) {
            throw new CalculationConstrainException(
                    "Sorry, it's not possible to multiply matrices with this rows and columns length.");
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