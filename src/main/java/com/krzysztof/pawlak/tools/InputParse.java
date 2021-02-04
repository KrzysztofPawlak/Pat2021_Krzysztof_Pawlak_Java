package com.krzysztof.pawlak.tools;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputParse {

    // DIGIT OR MATRIX / VECTOR
    // \\u0020 - space
    // ;(?!]) - semicolon can occur if next char is not "]" (regex lookahead)
    static final String ALLOWED_CHARS = "-?[0-9]+\\.?[0-9]*|\\[(\\u0020?-?[0-9]+\\.?[0-9]*\\u0020?(;(?!]))?){2,}]";
    static final String ONLY_ONE_NUMBER = "^\\u0020*-?[0-9]+\\.?[0-9]*\\u0020*$";

    public boolean isValid(String input) {
        return input.matches(ALLOWED_CHARS);
    }

    public boolean isOnlyOneNumber(String input) {
        return input.matches(ONLY_ONE_NUMBER);
    }

    public void isValidSyntaxThrowException(String input) {
        if (!isValid(input)) {
            throw new IllegalArgumentException(
                    "Sorry it's not a number, vector or matrix. Please try again or enter \"h\" to show help.");
        }
    }

    public Object parse(String input) {
        try {
            if (isIndicateToBeMatrixOrVector(input)) {
                input = clearSquareBrackets(input);
                if (!input.contains(";")) {
                    return parseToVectorRow(input);
                }
                final String[] rows = input.split(";");
                if (isOnlyOneNumber(rows[0])) {
                    return parseToVectorColumn(rows);
                }
                final String[] firstRow = rows[0].split(" ");
                final int rowsCount = rows.length;
                final int columnsCount = clearEmptyElements(firstRow).length;
                if (isVectorColumn(columnsCount)) {
                    return parseToVectorColumn(rows);
                }
                if (isMatrix(rowsCount, columnsCount)) {
                    return parseToMatrix(rows, columnsCount);
                }
            }
            return BigDecimal.valueOf(Double.parseDouble(input));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Can't parse input.");
        }
    }

    private BigDecimal[][] parseToMatrix(String[] rows, int columns) {
        final BigDecimal[][] result = new BigDecimal[rows.length][columns];
        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            final String[] valuesInRow = rows[rowIndex].split(" ");
            final String[] valuesNonEmpty = Arrays.stream(valuesInRow)
                    .filter(string -> !string.isEmpty())
                    .toArray(String[]::new);
            if (valuesNonEmpty.length != columns) {
                throw new IllegalArgumentException();
            }
            for (int columnIndex = 0; columnIndex < valuesNonEmpty.length; columnIndex++) {
                result[rowIndex][columnIndex] = BigDecimal.valueOf(Double.parseDouble(valuesNonEmpty[columnIndex]));
            }
        }
        return result;
    }

    private Vector<BigDecimal> parseToVectorColumn(String[] rows) {
        final Vector<BigDecimal> result = new Vector<>();
        for (String row : rows) {
            if (!isOnlyOneNumber(row)) {
                throw new IllegalArgumentException();
            }
            final double resultToAdd = Double.parseDouble(row.replace(" ", ""));
            result.add(BigDecimal.valueOf(resultToAdd));
        }
        return result;
    }

    private Vector<BigDecimal> parseToVectorRow(String row) {
        return Stream.of(row.split(" "))
                .filter(value -> !value.isEmpty())
                .map(Double::parseDouble)
                .map(BigDecimal::new)
                .collect(Collectors.toCollection(Vector::new));
    }

    private boolean isMatrix(int rowsCount, int columnsCount) {
        final int minimalRowsAndColumns = 1;
        return rowsCount > minimalRowsAndColumns && columnsCount > minimalRowsAndColumns;
    }

    private boolean isVectorColumn(int size) {
        final int minimalColumns = 1;
        return size == minimalColumns;
    }

    private String clearSquareBrackets(String input) {
        return input.replace("[", "").replace("]", "");
    }

    private boolean isIndicateToBeMatrixOrVector(String input) {
        return input.startsWith("[") && input.endsWith("]");
    }

    private String[] clearEmptyElements(String[] input) {
        return Arrays.stream(input)
                .filter(string -> !string.isEmpty())
                .toArray(String[]::new);
    }
}