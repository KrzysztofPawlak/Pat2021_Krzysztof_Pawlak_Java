package com.krzysztof.pawlak;

import java.math.BigDecimal;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputParse {

    // DIGIT OR MATRIX / VECTOR
    // \\u0020 - space
    // ;(?!]) - semicolon can occur if next char is not "]" (regex lookahead)
    static final String ALLOWED_CHARS = "-?[0-9]+\\.?[0-9]*|\\[(\\u0020?-?[0-9]+\\.?[0-9]*\\u0020?(;(?!]))?){2,}]";

    public boolean isValid(String input) {
        return input.matches(ALLOWED_CHARS);
    }

    public void isValidThrowException(String input) {
        if (!isValid(input)) {
            throw new IllegalArgumentException("invalid syntax");
        }
    }

    public Object parse(String input) {
        try {
            if (input.startsWith("[") && input.endsWith("]")) {
                input = input.replace("[", "");
                input = input.replace("]", "");
                if (!input.contains(";")) {
                    return parseToVectorRow(input);
                }
                String[] rows = input.split(";");
                int rowsCount = rows.length;
                int columnsCount = rows[0].split(" ").length;
                if (columnsCount == 1) {
                    return parseToVectorColumn(rows);
                }
                if (rowsCount > 1 && columnsCount > 1) {
                    return parseToMatrix(rows, columnsCount);
                }
            }
            return BigDecimal.valueOf(Double.parseDouble(input));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("can't parse input");
        }
    }

    private BigDecimal[][] parseToMatrix(String[] rows, int columns) {
        BigDecimal[][] result = new BigDecimal[rows.length][columns];
        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            String[] valuesInRow = rows[rowIndex].split(" ");
            if (valuesInRow.length != columns) {
                throw new IllegalArgumentException();
            }
            for (int columnIndex = 0; columnIndex < valuesInRow.length; columnIndex++) {
                result[rowIndex][columnIndex] = BigDecimal.valueOf(Double.parseDouble(valuesInRow[columnIndex]));
            }
        }
        return result;
    }

    private Vector<BigDecimal> parseToVectorColumn(String[] rows) {
        Vector<BigDecimal> result = new Vector<>();
        for (String row : rows) {
            String[] valuesInRow = row.split(" ");
            if (valuesInRow.length != 1) {
                throw new IllegalArgumentException();
            }
            result.add(BigDecimal.valueOf(Double.parseDouble(valuesInRow[0])));
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
}