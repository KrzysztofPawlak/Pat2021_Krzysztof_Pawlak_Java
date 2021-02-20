package com.krzysztof.pawlak.history;

import com.krzysztof.pawlak.models.ValueContainer;

import java.util.Deque;

public interface HistoryOperation {

    void writeEntry(Deque<ValueContainer> deque, ValueContainer result, String operator);
    boolean removeHistory();
    byte[] readRecent();
    byte[] readByRange(int from, int to);
}