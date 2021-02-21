package com.krzysztof.pawlak.error;

public class UnprocessableException extends RuntimeException {

    private final String description;

    public UnprocessableException(String message) {
        super();
        this.description = message;
    }

    public String getDescription() {
        return description;
    }
}