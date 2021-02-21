package com.krzysztof.pawlak.error;

public class ResourceNotFoundException extends RuntimeException {

    private final String description;

    public ResourceNotFoundException(String text) {
        super();
        this.description = text;
    }

    public String getDescription() {
        return description;
    }
}