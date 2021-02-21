package com.krzysztof.pawlak.history;

import com.krzysztof.pawlak.models.Range;
import com.krzysztof.pawlak.models.ValueContainer;
import com.krzysztof.pawlak.tools.FileLoaderService;
import com.krzysztof.pawlak.tools.RangeFileLogReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.krzysztof.pawlak.config.AppConfig.LOG_ROTATION_LINE_LENGTH;

public class HistoryService implements HistoryOperation {

    public static final String FILENAME = "historia_obliczen.txt";
    private int linesAmount;
    Logger logger = LoggerFactory.getLogger(HistoryService.class);
    private final FileLoaderService fileLoaderService;
    private final HistoryLogMaker historyLogMaker;

    public HistoryService(FileLoaderService fileLoaderService, HistoryLogMaker historyLogMaker) {
        this.fileLoaderService = fileLoaderService;
        this.historyLogMaker = historyLogMaker;
        linesAmount = FileLoaderService.countLine(FILENAME);
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
            logger.error("removeHistory() - can't remove log files");
            return false;
        }
    }

    @Override
    public byte[] readRecent() {
        return fileLoaderService.getFileAsByteArr(FILENAME);
    }

    @Override
    public byte[] readByRange(Range range) {
        List<String> historicalFiles = getListOfFiles();
        final var rangeLogReader = new RangeFileLogReader(historicalFiles, range);
        return rangeLogReader.read();
    }

    public byte[] readSpecificHistoryFile(String filename) {
        return fileLoaderService.getFileAsByteArr(filename);
    }

    public List<String> getListOfFiles() {
        try (Stream<String> stream = Files.list(FileLoaderService.getDirPath())
                .map(path -> path.getFileName().toString())) {
            return stream
                    .filter(filename -> filename.contains(FILENAME))
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.info("getListOfFiles() - no files in history");
            return Collections.emptyList();
        }
    }

    private void moveFileIfMaxLimitExceed() {
        if (linesAmount >= LOG_ROTATION_LINE_LENGTH) {
            try {
                final int nextFileNumber = getLastLogNumber() + 1;
                final String nextFilename = FILENAME + "." + nextFileNumber;
                Files.move(FileLoaderService.getPath(FILENAME), FileLoaderService.getPath(nextFilename));
                linesAmount = 0;
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("moveFileIfMaxLimitExceed() - can't move log file");
            }
        }
    }

    private void removeHistoryFile(String filename) throws IOException {
        try {
            Files.deleteIfExists(FileLoaderService.getPath(filename));
            logger.info("removeHistoryFile() - file deleted: ".concat(filename));
        } catch (IOException e) {
            logger.error("removeHistoryFile() - Unable to delete file: ".concat(filename));
            throw e;
        }
    }

    private int getLastLogNumber() {
        try (Stream<String> stream = Files.list(FileLoaderService.getDirPath())
                .map(path -> path.getFileName().toString())) {
            return stream
                    .filter(filename -> filename.contains(FILENAME))
                    .map(filename -> filename.replace(FILENAME + ".", ""))
                    .filter(HistoryService::isNumeric)
                    .mapToInt(Integer::valueOf)
                    .max()
                    .orElse(0);
        } catch (IOException e) {
            logger.info("getLastLogNumber() - no files");
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