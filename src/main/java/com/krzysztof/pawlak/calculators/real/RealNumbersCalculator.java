package com.krzysztof.pawlak.calculators.real;

import com.krzysztof.pawlak.calculators.Suggestive;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RealNumbersCalculator implements Suggestive {

    private enum Operations {
        ADD(0),
        SUBTRACT(1),
        MULTIPLY(2),
        DIVIDE(3),
        EXP(4),
        SQRT(5);

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

    public BigDecimal calculate(BigDecimal number, BigDecimal number2, int operation) {
        var selectedOperation = Operations.valueOf(operation);
        switch (selectedOperation) {
            case ADD:
                return add(number, number2);
            case SUBTRACT:
                return subtract(number, number2);
            case MULTIPLY:
                return multiply(number, number2);
            case DIVIDE:
                return divide(number, number2);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public BigDecimal add(BigDecimal number, BigDecimal number2) {
        return number.add(number2);
    }

    public BigDecimal subtract(BigDecimal number, BigDecimal number2) {
        return number.subtract(number2);
    }

    public BigDecimal multiply(BigDecimal number, BigDecimal number2) {
        return number.multiply(number2);
    }

    public BigDecimal divide(BigDecimal number, BigDecimal number2) {
        return number.divide(number2);
    }

    public BigDecimal exp(BigDecimal number, int exponent) {
        return number.pow(exponent);
    }

    public BigDecimal sqrt(BigDecimal number) {
        return number.sqrt(new MathContext(10));
    }

    @Override
    public List<String> suggest() {
        return Stream.of(Operations.values()).map(Enum::toString).collect(Collectors.toList());
    }
}