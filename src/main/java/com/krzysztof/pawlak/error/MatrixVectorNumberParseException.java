package com.krzysztof.pawlak.error;

public class MatrixVectorNumberParseException extends RuntimeException {

    private final String description;

    public MatrixVectorNumberParseException(String message) {
        super();
        this.description = message;
    }

    public String getDescription() {
        return description;
    }
}