package com.krzysztof.pawlak.error;

public class CalculationConstrainException extends RuntimeException {

    private final String description;

    public CalculationConstrainException(String message) {
        super();
        this.description = message;
    }

    public String getDescription() {
        return description;
    }
}