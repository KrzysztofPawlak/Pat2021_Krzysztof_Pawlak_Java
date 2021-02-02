package com.krzysztof.pawlak.calculators.matrix;

import com.krzysztof.pawlak.calculators.Suggestive;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MatrixByVectorCalculator implements Suggestive {

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

    public BigDecimal[][] calculate(BigDecimal[][] matrix, Vector<BigDecimal> vector, int operation) {
        var selectedOperation = Operations.valueOf(operation);
        switch (selectedOperation) {
            case MULTIPLY:
                //return multiply(matrix, vector); // TODO
            default:
                throw new UnsupportedOperationException();
        }
    }

    public Vector<BigDecimal> multiply(double[][] matrix, Vector<BigDecimal> vector) {
        return Arrays.stream(matrix).map(row ->
                IntStream.range(0, row.length)
                        .mapToObj(indexColumn -> BigDecimal.valueOf(row[indexColumn]).multiply(vector.get(indexColumn)))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .collect(Collectors.toCollection(Vector::new));
    }

    @Override
    public List<String> suggest() {
        return Stream.of(Operations.values()).map(Enum::toString).collect(Collectors.toList());
    }
}