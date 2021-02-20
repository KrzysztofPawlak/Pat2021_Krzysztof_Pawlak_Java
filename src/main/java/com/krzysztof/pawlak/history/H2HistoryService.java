package com.krzysztof.pawlak.history;

import com.krzysztof.pawlak.models.ValueContainer;
import com.krzysztof.pawlak.models.db.DbHistory;

import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class H2HistoryService implements HistoryOperation {

    private final HistoryRepository historyRepository;
    private final HistoryLogMaker historyLogMaker;

    public H2HistoryService(HistoryRepository historyRepository, HistoryLogMaker historyLogMaker) {
        this.historyRepository = historyRepository;
        this.historyLogMaker = historyLogMaker;
    }

    @Override
    public void writeEntry(Deque<ValueContainer> deque, ValueContainer result, String operator) {
        final var logToAppend = historyLogMaker.createEntryString(deque, result, operator);
        historyRepository.save(new DbHistory(logToAppend));
    }

    @Override
    public boolean removeHistory() {
        historyRepository.deleteAll();
        return true;
    }

    @Override
    public byte[] readRecentHistoryFile() {
        return historyRepository.findAll().stream()
                .map(DbHistory::getOperation)
                .collect(Collectors.joining(System.lineSeparator()))
                .getBytes();
    }

    @Override
    public byte[] readSpecificHistoryFile(String filename) {
        return null;
    }

    @Override
    public List<String> getListOfFiles() {
        return null;
    }
}