package com.krzysztof.pawlak.calculators.real;

import com.krzysztof.pawlak.calculators.Calculator;
import com.krzysztof.pawlak.error.CalculationConstrainException;
import com.krzysztof.pawlak.error.CalculationNotImplementedException;
import com.krzysztof.pawlak.models.OperationChar;
import com.krzysztof.pawlak.models.ValueContainer;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SqrtCalculator implements Calculator {

    private enum Operations {
        SQRT(1);

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
                    .findFirst()
                    .orElseThrow(CalculationNotImplementedException::new);
        }
    }

    @Override
    public BigDecimal calculate(Deque<ValueContainer> deque, int operation) {
        final var selectedOperation = Operations.valueOf(operation);
        final var value = (BigDecimal) deque.peekFirst().getValue();
        switch (selectedOperation) {
            case SQRT:
                return sqrt(value);
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override
    public Object calculate(Deque<ValueContainer> deque, OperationChar operation) {
        return null;
    }

    public BigDecimal sqrt(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) < 0) {
            throw new CalculationConstrainException("Sorry, it's not possible get square from negative number.");
        }
        return number.sqrt(new MathContext(10));
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