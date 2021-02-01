package com.krzysztof.pawlak.calculators.matrix;

import com.krzysztof.pawlak.calculators.Suggestive;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatrixByVectorCalculator implements Suggestive {

    public Vector<BigDecimal> multiply(double[][] matrix, Vector<BigDecimal> vector) {
        return Arrays.stream(matrix).map(row ->
                IntStream.range(0, row.length)
                        .mapToObj(indexColumn -> BigDecimal.valueOf(row[indexColumn]).multiply(vector.get(indexColumn)))
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .collect(Collectors.toCollection(Vector::new));
    }

    @Override
    public List<String> suggest() {
        return List.of("multiply");
    }
}