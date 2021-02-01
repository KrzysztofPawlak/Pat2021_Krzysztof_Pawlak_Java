package com.krzysztof.pawlak.calculators.matrix;

import com.krzysztof.pawlak.calculators.Suggestive;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class MatrixByNumberCalculator implements Suggestive {

    public BigDecimal[][] multiply(BigDecimal[][] matrix, BigDecimal number) {
        return Arrays.stream(matrix).map(row ->
                Arrays.stream(row).map(value -> value.multiply(number))
                        .toArray(BigDecimal[]::new))
                .toArray(BigDecimal[][]::new);
    }

    @Override
    public List<String> suggest() {
        return List.of("multiply");
    }
}