package com.krzysztof.pawlak.calculators.vector;

import com.krzysztof.pawlak.calculators.Suggestive;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class VectorByNumberCalculator implements Suggestive {

    public Vector multiply(Vector vector, double number) {
        return Arrays.stream(vector.toArray())
                .map(e -> (double) e * number)
                .collect(Collectors.toCollection(Vector::new));
    }

    @Override
    public List<String> suggest() {
        return List.of("multiply");
    }
}