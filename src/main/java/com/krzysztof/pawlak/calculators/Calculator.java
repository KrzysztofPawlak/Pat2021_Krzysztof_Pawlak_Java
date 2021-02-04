package com.krzysztof.pawlak.calculators;

import com.krzysztof.pawlak.models.ValueContainer;

import java.util.Deque;

public interface Calculator extends Suggestive {

    Object calculate(Deque<ValueContainer> deque, int operation);
}