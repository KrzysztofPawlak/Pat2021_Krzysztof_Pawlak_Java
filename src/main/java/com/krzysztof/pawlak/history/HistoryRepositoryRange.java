package com.krzysztof.pawlak.history;

import com.krzysztof.pawlak.models.db.DbHistory;

import java.util.List;

public interface HistoryRepositoryRange {
    List<DbHistory> findByLimitAndOffset(int limit, int offset);
}