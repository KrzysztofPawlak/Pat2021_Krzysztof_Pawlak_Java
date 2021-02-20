package com.krzysztof.pawlak.history;

import com.krzysztof.pawlak.models.db.DbHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<DbHistory, String>, HistoryRepositoryRange {
}