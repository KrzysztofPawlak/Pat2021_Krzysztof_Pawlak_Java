package com.krzysztof.pawlak.calculators.vector;

import com.krzysztof.pawlak.calculators.Suggestive;

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
                map.put(operation.value, map);
            }
        }

        static Operations valueOf(int operation) {
            return (Operations) map.get(operation);
        }
    }

    public Vector calculate(Vector vector, double number, int operation) {
        var selectedOperation = Operations.valueOf(operation);
        switch (selectedOperation) {
            case MULTIPLY:
                return multiply(vector, number);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public Vector multiply(Vector vector, double number) {
        return Arrays.stream(vector.toArray())
                .map(e -> (double) e * number)
                .collect(Collectors.toCollection(Vector::new));
    }

    @Override
    public List<String> suggest() {
        return Stream.of(Operations.values()).map(Enum::toString).collect(Collectors.toList());
    }
}