package com.krzysztof.pawlak.calculators.vector;

import com.krzysztof.pawlak.calculators.Calculator;
import com.krzysztof.pawlak.error.CalculationNotImplementedException;
import com.krzysztof.pawlak.models.OperationChar;
import com.krzysztof.pawlak.models.ValueContainer;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class VectorByVectorCalculator implements Calculator {

    private enum Operations {
        ADD(1),
        SUBTRACT(2);

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
        final var value = (Vector<BigDecimal>) deque.peekFirst().getValue();
        final var value2 = (Vector<BigDecimal>) deque.peekLast().getValue();
        switch (selectedOperation) {
            case ADD:
                return add(value, value2);
            case SUBTRACT:
                return subtract(value, value2);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public Vector<BigDecimal> add(Vector<BigDecimal> vector, Vector<BigDecimal> vector2) {
        if (vector.size() != vector2.size()) {
            throw new IllegalArgumentException("Sorry, it's not possible to add vectors with different length.");
        }
        return IntStream.range(0, vector.size())
                .mapToObj(position -> vector.get(position).add(vector2.get(position)))
                .collect(Collectors.toCollection(Vector::new));
    }

    public Vector<BigDecimal> subtract(Vector<BigDecimal> vector, Vector<BigDecimal> vector2) {
        if (vector.size() != vector2.size()) {
            throw new IllegalArgumentException("Sorry, it's not possible to subtract vectors with different length.");
        }
        return IntStream.range(0, vector.size())
                .mapToObj(position -> vector.get(position).subtract(vector2.get(position)))
                .collect(Collectors.toCollection(Vector::new));
    }

    @Override
    public List<String> suggest() {
        return Stream.of(Operations.values()).map(Enum::toString).collect(Collectors.toList());
    }
}