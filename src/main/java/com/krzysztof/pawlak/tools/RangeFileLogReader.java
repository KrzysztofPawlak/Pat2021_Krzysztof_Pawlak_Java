package com.krzysztof.pawlak.tools;

import com.krzysztof.pawlak.models.Range;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.krzysztof.pawlak.history.HistoryService.FILENAME;

public class RangeFileLogReader {

    private final List<String> files;
    private final Range range;
    private int currentPosition = 0;
    private final StringBuilder output = new StringBuilder();

    public RangeFileLogReader(List<String> files, Range range) {
        this.files = files;
        this.range = range;
    }

    public byte[] read() {
        List<String> historicalFiles = getFileListWithCurrentLogFileAtTheEnd();
        historicalFiles.stream().takeWhile(filename -> shouldAppend()).forEach(filename -> {
            int linesInFile = FileLoaderService.countLine(filename);
            if (shouldSkipFileIfLessThenFrom(linesInFile)) {
                currentPosition += linesInFile;
                return;
            }
            appendFromFile(filename);
        });
        return output.toString().getBytes();
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
            e.printStackTrace();
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