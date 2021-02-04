package com.krzysztof.pawlak.calculators.real;

import com.krzysztof.pawlak.calculators.Calculator;
import com.krzysztof.pawlak.config.AppConfig;
import com.krzysztof.pawlak.models.ValueContainer;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RealNumbersCalculator implements Calculator {

    private enum Operations {
        ADD(1),
        SUBTRACT(2),
        MULTIPLY(3),
        DIVIDE(4),
        EXP(5);

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

    @Override
    public BigDecimal calculate(Deque<ValueContainer> deque, int operation) {
        final var selectedOperation = Operations.valueOf(operation);
        final var value = (BigDecimal) deque.peekFirst().getValue();
        final var value2 = (BigDecimal) deque.peekLast().getValue();
        switch (selectedOperation) {
            case ADD:
                return add(value, value2);
            case SUBTRACT:
                return subtract(value, value2);
            case MULTIPLY:
                return multiply(value, value2);
            case DIVIDE:
                return divide(value, value2);
            case EXP:
                return exp(value, value2.intValue());
            default:
                throw new UnsupportedOperationException();
        }
    }

    public BigDecimal add(BigDecimal number, BigDecimal number2) {
        return number.add(number2).stripTrailingZeros();
    }

    public BigDecimal subtract(BigDecimal number, BigDecimal number2) {
        return number.subtract(number2).stripTrailingZeros();
    }

    public BigDecimal multiply(BigDecimal number, BigDecimal number2) {
        return number.multiply(number2).stripTrailingZeros();
    }

    public BigDecimal divide(BigDecimal number, BigDecimal number2) {
        if (number2.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Cannot divide by zero!!!");
        }
        return number.divide(number2).stripTrailingZeros();
    }

    public BigDecimal exp(BigDecimal number, int exponent) {
        if (exponent > AppConfig.MAX_EXPONENT_VALUE) {
            throw new IllegalArgumentException("Sorry, max supported exponent is: " + AppConfig.MAX_EXPONENT_VALUE);
        }
        return number.pow(exponent).stripTrailingZeros();
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