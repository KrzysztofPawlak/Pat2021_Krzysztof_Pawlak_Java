package com.krzysztof.pawlak.tools;

import com.krzysztof.pawlak.models.InputType;
import com.krzysztof.pawlak.models.ValueContainer;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Deque;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.krzysztof.pawlak.config.AppConfig.LOG_ROTATION_LINE_LENGTH;

public class HistoryWriter {

    private FileWriter fileWriter;
    private static final String FILENAME = "historia_obliczen.txt";
    private int linesAmount;

    public HistoryWriter() {
        try {
            linesAmount = countLine();
            fileWriter = new FileWriter(FILENAME, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeEntry(Deque<ValueContainer> deque, ValueContainer result, String operator) {
        try {
            moveFileIfMaxLimitExceed();
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
            linesAmount++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveFileIfMaxLimitExceed() {
        if (linesAmount >= LOG_ROTATION_LINE_LENGTH) {
            try {
                fileWriter.close();
                final int nextFileNumber = getLastLogNumber() + 1;
                final String nextFilename = FILENAME + "." + nextFileNumber;
                Files.move(Paths.get(System.getProperty("user.dir"), FILENAME),
                        Paths.get(System.getProperty("user.dir"), nextFilename));
                linesAmount = 0;
                fileWriter = new FileWriter(FILENAME, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private int countLine() {
        try (Stream<String> stream = Files.lines(Paths.get(System.getProperty("user.dir"), FILENAME), StandardCharsets.UTF_8)) {
            return Math.toIntExact(stream.count());
        } catch (IOException e) {
            return 0;
        }
    }

    private int getLastLogNumber() {
        try (Stream<String> stream = Files.list(Path.of(System.getProperty("user.dir")))
                .map(path -> path.getFileName().toString())) {
            return stream
                    .filter(filename -> filename.contains(FILENAME))
                    .map(filename -> filename.replace(FILENAME + ".", ""))
                    .filter(HistoryWriter::isNumeric)
                    .mapToInt(Integer::valueOf)
                    .max()
                    .orElse(0);
        } catch (IOException e) {
            return 0;
        }
    }

    private static boolean isNumeric(String input) {
        if (input == null) {
            return false;
        }
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}