package com.krzysztof.pawlak.tools;

import com.krzysztof.pawlak.error.ResourceNotFoundException;
import com.krzysztof.pawlak.error.UnprocessableException;
import com.krzysztof.pawlak.models.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.krzysztof.pawlak.history.file.HistoryService.FILENAME;

public class RangeFileLogReader {

    private final List<String> files;
    private final Range range;
    private int currentPosition = 0;
    private final StringBuilder output = new StringBuilder();
    Logger logger = LoggerFactory.getLogger(RangeFileLogReader.class);

    public RangeFileLogReader(List<String> files, Range range) {
        this.files = files;
        this.range = range;
    }

    public byte[] read() {
        List<String> historicalFiles = getFileListWithCurrentLogFileAtTheEnd();
        historicalFiles.stream().takeWhile(filename -> shouldAppend()).forEach(filename -> {
            final int linesInFile = FileLoaderService.countLine(filename);
            if (shouldSkipFileIfLessThenFrom(linesInFile)) {
                currentPosition += linesInFile;
                return;
            }
            appendFromFile(filename);
        });
        final var outputAsString = output.toString();
        if (outputAsString.isEmpty()) {
            logger.info("read() - logs not found");
            throw new ResourceNotFoundException("Logs not found!");
        }
        return outputAsString.getBytes();
    }

    private List<String> getFileListWithCurrentLogFileAtTheEnd() {
        List<String> historicalFiles = files.stream()
                .filter(filename -> !filename.equals(FILENAME))
                .collect(Collectors.toList());
        if (FileLoaderService.exists(FILENAME)) {
            historicalFiles.add(FILENAME);
        }
        return historicalFiles;
    }

    private void appendFromFile(String filename) {
        final var filenamePath = FileLoaderService.getPath(filename).toString();
        try (BufferedReader br = new BufferedReader(new FileReader(filenamePath))) {
            String line;
            while ((line = br.readLine()) != null && shouldAppend()) {
                currentPosition++;
                if (currentPosition >= range.getFrom()) {
                    output.append(line);
                    output.append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            logger.info("appendFromFile() - error during logs processing");
            throw new UnprocessableException("Error during logs processing.");
        }
    }

    private boolean shouldSkipFileIfLessThenFrom(int linesInFile) {
        return currentPosition + linesInFile < range.getFrom();
    }

    private boolean shouldAppend() {
        if (range.getTo() == 0) {
            return true;
        }
        return currentPosition < range.getTo();
    }
}