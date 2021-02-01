package com.krzysztof.pawlak.calculators.real;

import com.krzysztof.pawlak.calculators.Suggestive;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class RealNumbersCalculator implements Suggestive {

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
        return List.of("add", "subtract", "multiply", "divide", "exp", "sqrt");
    }
}