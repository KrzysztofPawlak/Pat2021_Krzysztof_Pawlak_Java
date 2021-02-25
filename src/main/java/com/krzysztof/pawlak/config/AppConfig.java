package com.krzysztof.pawlak.config;

public final class AppConfig {

    private AppConfig() {
    }

    public static final int MAX_EXPONENT_VALUE = 128;
    public static final int MAX_MATRIX_ROWS = 4;
    public static final int MAX_MATRIX_COLUMNS = 4;
    public static final int MAX_VECTOR_LENGTH = 4;
    public static final int LOG_ROTATION_LINE_LENGTH = 50;
    public static final int RECENT_HISTORICAL_DB_LOGS_LIMIT = 50;

    // NUMBER OR MATRIX / VECTOR
    // \\u0020 - space
    // ;(?!]) - semicolon can occur if next char is not "]" (regex lookahead)
    public static final String ALLOWED_CHARS = "-?[0-9]+\\.?[0-9]*|\\[(\\u0020?-?[0-9]+\\.?[0-9]*\\u0020?(;(?!]))?){2,}]";
}