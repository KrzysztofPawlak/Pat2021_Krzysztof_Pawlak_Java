package com.krzysztof.pawlak.tools;

import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.ValueContainer;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Deque;
import java.util.Vector;
import java.util.stream.Collectors;

public class HistoryWriter {

    private static FileWriter fileWriter;

    public HistoryWriter() {
        try {
            fileWriter = new FileWriter("historia_obliczen.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeEntry(Deque<ValueContainer> deque, ValueContainer result, String operator) {
        try {
            if (deque.size() == 1) {
                appendOperator(operator);
                append(deque.peekFirst());
            } else {
                append(deque.peekFirst());
                appendOperator(operator);
                append(deque.peekLast());
            }
            appendOperator("=");
            append(result);
            fileWriter.write(System.lineSeparator());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void append(ValueContainer value) throws IOException {
        if (value.getInputType() == InputType.NUMBER) {
            fileWriter.write(value.getValue().toString());
        }
        if (value.getInputType() == InputType.MATRIX) {
            String matrix = Arrays.stream(((BigDecimal[][]) value.getValue()))
                    .map(row -> Arrays.stream(row)
                            .map(BigDecimal::toString)
                            .collect(Collectors.joining(" "))
                    ).collect(Collectors.joining(";"));
            fileWriter.write("[" + matrix + "]");
        }
        if (value.getInputType() == InputType.VECTOR) {
            String vector = ((Vector<BigDecimal>) value.getValue()).stream()
                    .map(BigDecimal::toString)
                    .collect(Collectors.joining(" "));
            fileWriter.write("[" + vector + "]");
        }
    }

    private void appendOperator(String operator) throws IOException {
        fileWriter.write(" " + operator + " ");
    }
}