package com.krzysztof.pawlak.calculators.vector;

import com.krzysztof.pawlak.calculators.Calculator;
import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.OperationChar;
import com.krzysztof.pawlak.models.ValueContainer;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VectorByNumberCalculator implements Calculator {

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

        static Operations valueOf(OperationChar operation) {
            return Arrays.stream(Operations.values())
                    .filter(enumOperation -> enumOperation.toString().equals(operation.toString()))
                    .findFirst().orElseThrow(IllegalArgumentException::new);
        }
    }

    @Override
    public Vector<BigDecimal> calculate(Deque<ValueContainer> deque, int operation) {
        final var selectedOperation = Operations.valueOf(operation);
        final var value = deque.peekFirst();
        final var value2 = deque.peekLast();
        switch (selectedOperation) {
            case MULTIPLY:
                return (value.getInputType() == InputType.VECTOR && value2.getInputType() == InputType.NUMBER) ?
                        multiply((Vector<BigDecimal>) value.getValue(), (BigDecimal) value2.getValue()) :
                        multiply((Vector<BigDecimal>) value2.getValue(), (BigDecimal) value.getValue());
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public Object calculate(Deque<ValueContainer> deque, OperationChar operation) {
        final var selectedOperation = Operations.valueOf(operation);
        final var value = deque.peekFirst();
        final var value2 = deque.peekLast();
        switch (selectedOperation) {
            case MULTIPLY:
                return (value.getInputType() == InputType.VECTOR && value2.getInputType() == InputType.NUMBER) ?
                        multiply((Vector<BigDecimal>) value.getValue(), (BigDecimal) value2.getValue()) :
                        multiply((Vector<BigDecimal>) value2.getValue(), (BigDecimal) value.getValue());
            default:
                throw new UnsupportedOperationException();
        }
    }

    public Vector<BigDecimal> multiply(Vector<BigDecimal> vector, BigDecimal number) {
        return Arrays.stream(vector.toArray(BigDecimal[]::new))
                .map(vectorValue -> vectorValue.multiply(number))
                .collect(Collectors.toCollection(Vector::new));
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