package com.krzysztof.pawlak.calculators.real;

import com.krzysztof.pawlak.calculators.Calculator;
import com.krzysztof.pawlak.config.AppConfig;
import com.krzysztof.pawlak.error.CalculationConstrainException;
import com.krzysztof.pawlak.error.CalculationNotImplementedException;
import com.krzysztof.pawlak.models.OperationChar;
import com.krzysztof.pawlak.models.ValueContainer;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RealNumbersCalculator implements Calculator {

    private enum Operations {
        ADD(1),
        SUBTRACT(2),
        MULTIPLY(3),
        DIVIDE(4),
        EXP(5);

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
                throw new CalculationNotImplementedException();
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
            throw new CalculationConstrainException("Cannot divide by zero!!!");
        }
        return number.divide(number2, MathContext.DECIMAL128).stripTrailingZeros();
    }

    public BigDecimal exp(BigDecimal number, int exponent) {
        if (exponent > AppConfig.MAX_EXPONENT_VALUE) {
            throw new CalculationConstrainException("Sorry, max supported exponent is: " + AppConfig.MAX_EXPONENT_VALUE);
        }
        return number.pow(exponent).stripTrailingZeros();
    }

    @Override
    public List<String> suggest() {
        return Stream.of(Operations.values()).map(Enum::toString).collect(Collectors.toList());
    }
}