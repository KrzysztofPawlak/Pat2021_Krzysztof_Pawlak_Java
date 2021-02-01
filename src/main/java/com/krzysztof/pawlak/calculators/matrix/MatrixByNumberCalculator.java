package com.krzysztof.pawlak.calculators.matrix;

import com.krzysztof.pawlak.calculators.Suggestive;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatrixByNumberCalculator implements Suggestive {

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

    public BigDecimal[][] calculate(BigDecimal[][] matrix, BigDecimal number, int operation) {
        var selectedOperation = Operations.valueOf(operation);
        switch (selectedOperation) {
            case MULTIPLY:
                return multiply(matrix, number);
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
    public List<String> suggest() {
        return Stream.of(Operations.values()).map(Enum::toString).collect(Collectors.toList());
    }
}