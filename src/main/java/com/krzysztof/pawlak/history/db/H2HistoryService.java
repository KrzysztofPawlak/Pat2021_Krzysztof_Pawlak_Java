package com.krzysztof.pawlak.history.db;

import com.krzysztof.pawlak.history.HistoryLogMaker;
import com.krzysztof.pawlak.history.HistoryOperation;
import com.krzysztof.pawlak.models.Range;
import com.krzysztof.pawlak.models.ValueContainer;
import com.krzysztof.pawlak.models.db.DbHistory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Deque;
import java.util.stream.Collectors;

import static com.krzysztof.pawlak.config.AppConfig.RECENT_HISTORICAL_DB_LOGS_LIMIT;

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
    public byte[] readRecent() {
        final var pageable = PageRequest.of(0, RECENT_HISTORICAL_DB_LOGS_LIMIT,
                Sort.Direction.DESC, "id");
        return historyRepository.findAll(pageable).stream()
                .map(DbHistory::getOperation)
                .collect(Collectors.joining(System.lineSeparator()))
                .getBytes();
    }

    @Override
    public byte[] readByRange(Range range) {
        final var limit = calculateLimit(range.getFrom(), range.getTo());
        final var offset = calculateOffset(range.getFrom());
        return historyRepository.findByLimitAndOffset(limit, offset).stream()
                .map(DbHistory::getOperation)
                .collect(Collectors.joining(System.lineSeparator()))
                .getBytes();
    }
    
    private int calculateLimit(int from, int to) {
        if (to == 0) {
            return 0;
        }
        final var shiftToCompensateStartedFromFirstElement = 1;
        return to - from + shiftToCompensateStartedFromFirstElement;
    }
    
    private int calculateOffset(int from) {
        final var shiftToCompensateStartedFromFirstElement = 1;
        return from - shiftToCompensateStartedFromFirstElement;
    }
}