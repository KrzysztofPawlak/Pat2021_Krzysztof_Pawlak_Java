package com.krzysztof.pawlak.calculators;

import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class VectorCalculator {

    public Vector multiply(Vector vector, double number) {
        return Arrays.stream(vector.toArray())
                .map(e -> (double) e * number)
                .collect(Collectors.toCollection(Vector::new));
    }

    public Vector add(Vector vector, Vector vector2) {
        return IntStream.range(0, vector.size())
                .mapToObj(position -> (double) vector.get(position) + (double) vector2.get(position))
                .collect(Collectors.toCollection(Vector::new));
    }

    public Vector subtract(Vector vector, Vector vector2) {
        return IntStream.range(0, vector.size())
                .mapToObj(position -> (double) vector.get(position) - (double) vector2.get(position))
                .collect(Collectors.toCollection(Vector::new));
    }
}