package com.krzysztof.pawlak.error;

public class InputParseException extends RuntimeException {

    private final String description;

    public InputParseException(String message) {
        super();
        this.description = message;
    }

    public String getDescription() {
        return description;
    }
}