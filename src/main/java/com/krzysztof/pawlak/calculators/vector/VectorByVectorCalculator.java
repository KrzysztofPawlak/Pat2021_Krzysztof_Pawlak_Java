package com.krzysztof.pawlak.calculators.vector;

import com.krzysztof.pawlak.calculators.Suggestive;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class VectorByVectorCalculator implements Suggestive {

    private enum Operations {
        ADD(0),
        SUBTRACT(1);

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

    public Vector<BigDecimal> calculate(Vector<BigDecimal> vector, Vector<BigDecimal> vector2, int operation) {
        final var selectedOperation = Operations.valueOf(operation);
        switch (selectedOperation) {
            case ADD:
                return add(vector, vector2);
            case SUBTRACT:
                return subtract(vector, vector2);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public Vector<BigDecimal> add(Vector<BigDecimal> vector, Vector<BigDecimal> vector2) {
        return IntStream.range(0, vector.size())
                .mapToObj(position -> vector.get(position).add(vector2.get(position)))
                .collect(Collectors.toCollection(Vector::new));
    }

    public Vector<BigDecimal> subtract(Vector<BigDecimal> vector, Vector<BigDecimal> vector2) {
        return IntStream.range(0, vector.size())
                .mapToObj(position -> vector.get(position).subtract(vector2.get(position)))
                .collect(Collectors.toCollection(Vector::new));
    }

    @Override
    public List<String> suggest() {
        return Stream.of(Operations.values()).map(Enum::toString).collect(Collectors.toList());
    }
}