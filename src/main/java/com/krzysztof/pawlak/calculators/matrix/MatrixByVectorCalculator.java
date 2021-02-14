package com.krzysztof.pawlak.calculators.matrix;

import com.krzysztof.pawlak.calculators.Calculator;
import com.krzysztof.pawlak.error.CalculationNotImplementedException;
import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.OperationChar;
import com.krzysztof.pawlak.models.ValueContainer;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MatrixByVectorCalculator implements Calculator {

    private enum Operations {
        MULTIPLY(1);

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
    public Object calculate(Deque<ValueContainer> deque, OperationChar operation) {
        final var selectedOperation = Operations.valueOf(operation);
        final var value = deque.peekFirst();
        final var value2 = deque.peekLast();
        switch (selectedOperation) {
            case MULTIPLY:
                return (value.getInputType() == InputType.MATRIX && value2.getInputType() == InputType.VECTOR) ?
                        multiply((BigDecimal[][]) value.getValue(), (Vector<BigDecimal>) value2.getValue()) :
                        multiply((BigDecimal[][]) value2.getValue(), (Vector<BigDecimal>) value.getValue());
            default:
                throw new CalculationNotImplementedException();
        }
    }

    public Vector<BigDecimal> multiply(BigDecimal[][] matrix, Vector<BigDecimal> vector) {
        return Arrays.stream(matrix).map(row ->
                IntStream.range(0, row.length)
                        .mapToObj(indexColumn -> row[indexColumn].multiply(vector.get(indexColumn)))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .collect(Collectors.toCollection(Vector::new));
    }

    @Override
    public List<String> suggest() {
        return Stream.of(Operations.values()).map(Enum::toString).collect(Collectors.toList());
    }
}