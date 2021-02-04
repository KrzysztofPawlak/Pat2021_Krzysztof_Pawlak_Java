package com.krzysztof.pawlak.calculators.matrix;

import com.krzysztof.pawlak.calculators.Calculator;
import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.ValueContainer;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatrixByNumberCalculator implements Calculator {

    private enum Operations {
        MULTIPLY(1);

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
            case MULTIPLY:
                return (value.getInputType() == InputType.MATRIX && value2.getInputType() == InputType.NUMBER) ?
                        multiply((BigDecimal[][]) value.getValue(), (BigDecimal) value2.getValue()) :
                        multiply((BigDecimal[][]) value2.getValue(), (BigDecimal) value.getValue());
            default:
                throw new UnsupportedOperationException();
        }
    }

    public BigDecimal[][] multiply(BigDecimal[][] matrix, BigDecimal number) {
        return Arrays.stream(matrix).map(row ->
                Arrays.stream(row).map(value -> value.multiply(number))
                        .toArray(BigDecimal[]::new))
                .toArray(BigDecimal[][]::new);
    }

    @Override
    public String getOperationNameAsString(int selected) {
        return Operations.valueOf(selected).toString();
    }

    @Override
    public List<String> suggest() {
        return Stream.of(Operations.values()).map(Enum::toString).collect(Collectors.toList());
    }
}