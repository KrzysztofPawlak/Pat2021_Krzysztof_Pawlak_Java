package com.krzysztof.pawlak.calculator;

import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.ValueContainer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Collectors;

@Component
public class OutputConverter implements Converter<ValueContainer, String> {

    @Override
    public String convert(ValueContainer value) {
        if (value.getInputType() == InputType.NUMBER) {
            return value.getValue().toString();
        }
        if (value.getInputType() == InputType.MATRIX) {
            var matrix = Arrays.stream(((BigDecimal[][]) value.getValue()))
                    .map(row -> Arrays.stream(row)
                            .map(BigDecimal::toString)
                            .collect(Collectors.joining(" "))
                    ).collect(Collectors.joining(";"));
            return "[" + matrix + "]";
        }
        if (value.getInputType() == InputType.VECTOR) {
            var vector = ((Vector<BigDecimal>) value.getValue()).stream()
                    .map(BigDecimal::toString)
                    .collect(Collectors.joining(" "));
            return "[" + vector + "]";
        }
        throw new IllegalArgumentException();
    }
}