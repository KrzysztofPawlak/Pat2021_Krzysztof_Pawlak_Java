package com.krzysztof.pawlak;

public class InputParse {

    // DIGIT OR MATRIX / VECTOR
    // \\u0020 - space
    // ;(?!]) - semicolon can occur if next char is not "]" (regex lookahead)
    static final String ALLOWED_CHARS = "-?[0-9]+\\.?[0-9]*|\\[(\\u0020?-?[0-9]+\\.?[0-9]*\\u0020?(;(?!]))?){2,}]";

    public boolean isValid(String input) {
        return input.matches(ALLOWED_CHARS);
    }
}