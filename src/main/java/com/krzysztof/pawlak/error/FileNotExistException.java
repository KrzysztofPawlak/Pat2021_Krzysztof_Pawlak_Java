package com.krzysztof.pawlak.error;

public class FileNotExistException extends RuntimeException {

    private final String description;

    public FileNotExistException(String message) {
        super();
        this.description = message;
    }

    public FileNotExistException() {
        super();
        this.description = "File not exists.";
    }

    public String getDescription() {
        return description;
    }
}
