package com.krzysztof.pawlak.history;

import com.krzysztof.pawlak.models.ValueContainer;
import com.krzysztof.pawlak.tools.FileLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
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

public class HistoryService implements HistoryOperation {

    private static final String FILENAME = "historia_obliczen.txt";
    private int linesAmount;
    Logger logger = LoggerFactory.getLogger(HistoryService.class);
    private final FileLoaderService fileLoaderService;
    private final HistoryLogMaker historyLogMaker;

    public HistoryService(FileLoaderService fileLoaderService, HistoryLogMaker historyLogMaker) {
        this.fileLoaderService = fileLoaderService;
        this.historyLogMaker = historyLogMaker;
        linesAmount = countLine();
    }

    @Override
    public void writeEntry(Deque<ValueContainer> deque, ValueContainer result, String operator) {
        moveFileIfMaxLimitExceed();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            final var logToAppend = historyLogMaker.createEntryString(deque, result, operator);
            writer.write(logToAppend);
            writer.write(System.lineSeparator());
            writer.flush();
            linesAmount++;
        } catch (IOException e) {
            logger.error("writeEntry() - can't write to log file");
        }
    }

    @Override
    public boolean removeHistory() {
        try {
            List<String> listOfFiles = getListOfFiles();
            for (String filename : listOfFiles) {
                removeHistoryFile(filename);
            }
            linesAmount = 0;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public byte[] readRecent() {
        var path = Paths.get(System.getProperty("user.dir"), FILENAME);
        return fileLoaderService.getFileAsByteArr(path);
    }

    @Override
    public byte[] readByRange(int from, int to) {
        return new byte[0];
    }

    public byte[] readSpecificHistoryFile(String filename) {
        var path = Paths.get(System.getProperty("user.dir"), filename);
        return fileLoaderService.getFileAsByteArr(path);
    }

    public List<String> getListOfFiles() {
        try (Stream<String> stream = Files.list(Path.of(System.getProperty("user.dir")))
                .map(path -> path.getFileName().toString())) {
            return stream
                    .filter(filename -> filename.contains(FILENAME)).collect(Collectors.toList());
        } catch (IOException e) {
            logger.info("no files in history");
            return Collections.emptyList();
        }
    }

    private void moveFileIfMaxLimitExceed() {
        if (linesAmount >= LOG_ROTATION_LINE_LENGTH) {
            try {
                final int nextFileNumber = getLastLogNumber() + 1;
                final String nextFilename = FILENAME + "." + nextFileNumber;
                Files.move(Paths.get(System.getProperty("user.dir"), FILENAME),
                        Paths.get(System.getProperty("user.dir"), nextFilename));
                linesAmount = 0;
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("moveFileIfMaxLimitExceed() - can't move log file");
            }
        }
    }

    private int countLine() {
        try (Stream<String> stream = Files.lines(Paths.get(System.getProperty("user.dir"), FILENAME),
                StandardCharsets.UTF_8)) {
            return Math.toIntExact(stream.count());
        } catch (IOException e) {
            logger.info("countLine() - Unable to read file: ".concat(FILENAME));
            return 0;
        }
    }

    private void removeHistoryFile(String filename) throws IOException {
        try {
            Files.deleteIfExists(Path.of(System.getProperty("user.dir"), filename));
            logger.info("file deleted: ".concat(filename));
        } catch (IOException e) {
            logger.error("removeHistory() - Unable to delete file: ".concat(filename));
            throw e;
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