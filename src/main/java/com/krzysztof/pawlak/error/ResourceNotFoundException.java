package com.krzysztof.pawlak.error;

public class ResourceNotFoundException extends RuntimeException {

    private final String description;

    public ResourceNotFoundException(String message) {
        super();
        this.description = message;
    }

    public String getDescription() {
        return description;
    }
}