package com.krzysztof.pawlak.history;

import com.krzysztof.pawlak.calculator.OutputConverter;
import com.krzysztof.pawlak.models.ValueContainer;
import com.krzysztof.pawlak.tools.FileLoaderService;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.krzysztof.pawlak.config.AppConfig.LOG_ROTATION_LINE_LENGTH;

@Service
public class HistoryService {

    private FileWriter fileWriter;
    private static final String FILENAME = "historia_obliczen.txt";
    private int linesAmount;
    private OutputConverter outputConverter = new OutputConverter();
    private FileLoaderService fileLoaderService = new FileLoaderService();

    public HistoryService() {
        try {
            linesAmount = countLine();
            fileWriter = new FileWriter(FILENAME, true);
        } catch (IOException e) {
            System.out.println("can't open log file");
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
            System.out.println("can't write to log file");
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
                System.out.println("can't move log file");
            }
        }
    }

    private void append(ValueContainer value) throws IOException {
        fileWriter.write(outputConverter.convert(value));
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

    public List<String> getListOfFiles() {
        try (Stream<String> stream = Files.list(Path.of(System.getProperty("user.dir")))
                .map(path -> path.getFileName().toString())) {
            return stream
                    .filter(filename -> filename.contains(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private int getLastLogNumber() {
        try (Stream<String> stream = Files.list(Path.of(System.getProperty("user.dir")))
                .map(path -> path.getFileName().toString())) {
            return stream
                    .filter(filename -> filename.contains(FILENAME))
                    .map(filename -> filename.replace(FILENAME + ".", ""))
                    .filter(HistoryService::isNumeric)
                    .mapToInt(Integer::valueOf)
                    .max()
                    .orElse(0);
        } catch (IOException e) {
            return 0;
        }
    }

    public byte[] readRecentHistoryFile() {
        var path = Paths.get(System.getProperty("user.dir"), FILENAME);
        return fileLoaderService.getFileAsByteArr(path);
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