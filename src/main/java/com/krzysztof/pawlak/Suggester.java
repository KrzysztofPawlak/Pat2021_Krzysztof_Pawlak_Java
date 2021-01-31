package com.krzysztof.pawlak;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;
import java.util.stream.IntStream;

public class Suggester<V> {

    public List<String> suggest(Vector vector, Vector vector2) {
        return List.of("add", "subtract");
    }

    public List<String> suggest(Vector vector, BigDecimal number) {
        return List.of("multiply");
    }

    public void print(List<String> list) {
        IntStream.of(0, list.size()).forEach(index -> System.out.println(index + ". " + list.get(index)));
    }
}