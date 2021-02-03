package com.krzysztof.pawlak.calculators.vector;

import com.krzysztof.pawlak.calculators.Suggestive;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VectorByNumberCalculator implements Suggestive {

    private enum Operations {
        MULTIPLY(0);

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

    public Vector calculate(Vector<BigDecimal> vector, BigDecimal number, int operation) {
        final var selectedOperation = Operations.valueOf(operation);
        switch (selectedOperation) {
            case MULTIPLY:
                return multiply(vector, number);
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
    public List<String> suggest() {
        return Stream.of(Operations.values()).map(Enum::toString).collect(Collectors.toList());
    }
}