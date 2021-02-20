package com.krzysztof.pawlak.history;

import com.krzysztof.pawlak.models.ValueContainer;

import java.util.Deque;
import java.util.List;

public interface HistoryOperation {

    void writeEntry(Deque<ValueContainer> deque, ValueContainer result, String operator);
    boolean removeHistory();
    byte[] readRecentHistoryFile();
    byte[] readSpecificHistoryFile(String filename);
    List<String> getListOfFiles();
}
