package com.krzysztof.pawlak.error;

public class CalculationNotImplementedException extends RuntimeException {

    private final String description;

    public CalculationNotImplementedException(String message) {
        super();
        this.description = message;
    }

    public CalculationNotImplementedException() {
        super();
        this.description = "Calculation for this types of data is impossible or not implemented.";
    }

    public String getDescription() {
        return description;
    }
}
