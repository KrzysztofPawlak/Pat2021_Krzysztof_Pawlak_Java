package com.krzysztof.pawlak.calculators;

import com.krzysztof.pawlak.models.Matrix;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatrixCalculator {

    public Vector<BigDecimal> multiply(double[][] matrix, Vector<BigDecimal> vector) {
        return Arrays.stream(matrix).map(row -> IntStream.range(0, row.length)
                .mapToObj(indexColumn -> BigDecimal.valueOf(row[indexColumn]).multiply(vector.get(indexColumn)))
                .reduce(BigDecimal.ZERO, BigDecimal::add))
                .collect(Collectors.toCollection(Vector::new));
    }

    public Matrix add(Matrix matrix, Matrix matrix2) {
        Matrix v = new Matrix();
        return v;
    }

    public Matrix subtract(Matrix matrix, Matrix matrix2) {
        Matrix v = new Matrix();
        return v;
    }
}