package com.krzysztof.pawlak.models;

public enum OperationChar {

    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    EXP("^"),
    SQRT("\u221A");

    private final String value;

    OperationChar(String value) {
        this.value = value;
    }

    public String getRepresentation() {
        return this.value;
    }
}