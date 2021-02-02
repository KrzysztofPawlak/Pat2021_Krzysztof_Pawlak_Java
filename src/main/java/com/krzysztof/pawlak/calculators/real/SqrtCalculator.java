package com.krzysztof.pawlak.calculators.real;

import com.krzysztof.pawlak.calculators.Suggestive;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SqrtCalculator implements Suggestive {

    private enum Operations {
        SQRT(0);

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

    public BigDecimal calculate(BigDecimal number, int operation) {
        var selectedOperation = Operations.valueOf(operation);
        switch (selectedOperation) {
            case SQRT:
                return sqrt(number);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public BigDecimal sqrt(BigDecimal number) {
        return number.sqrt(new MathContext(10));
    }

    @Override
    public List<String> suggest() {
        return Stream.of(Operations.values()).map(Enum::toString).collect(Collectors.toList());
    }
}