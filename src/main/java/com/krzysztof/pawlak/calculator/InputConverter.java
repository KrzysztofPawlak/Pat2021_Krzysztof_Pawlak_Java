package com.krzysztof.pawlak.calculator;

import com.krzysztof.pawlak.models.Input;
import com.krzysztof.pawlak.models.ValueContainer;
import com.krzysztof.pawlak.tools.InputParse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

@Component
public class InputConverter implements Converter<Input, Deque<ValueContainer>> {

    private final InputParse inputParse = new InputParse();

    @Override
    public Deque<ValueContainer> convert(Input input) {
        return input.getValues().stream()
                .map(inputParse::parse)
                .map(ValueContainer::new)
                .collect(Collectors.toCollection(ArrayDeque::new));
    }
}