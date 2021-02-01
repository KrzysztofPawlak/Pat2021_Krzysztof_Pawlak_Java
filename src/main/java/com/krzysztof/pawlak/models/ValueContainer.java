package com.krzysztof.pawlak.models;

import java.math.BigDecimal;
import java.util.Vector;

public class ValueContainer {

    private Object value;
    private InputType inputType;

    public ValueContainer(Object value) {
        this.value = value;
        determineInputType(value);
    }

    private void determineInputType(Object o) {
        if (o instanceof BigDecimal[][]) {
            this.inputType = InputType.MATRIX;
        } else if (o instanceof Vector) {
            this.inputType = InputType.VECTOR;
        } else {
            this.inputType = InputType.NUMBER;
        }
    }

    public Object getValue() {
        return value;
    }

    public InputType getInputType() {
        return inputType;
    }
}