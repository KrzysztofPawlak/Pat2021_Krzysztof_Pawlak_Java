package com.krzysztof.pawlak.history;

import com.krzysztof.pawlak.models.db.DbHistory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class HistoryRepositoryRangeImpl implements HistoryRepositoryRange {

    private final EntityManager entityManager;

    HistoryRepositoryRangeImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<DbHistory> findByLimitAndOffset(int limit, int offset) {
        final var criteriaBuilder = entityManager.getCriteriaBuilder();
        final var criteriaQuery = criteriaBuilder.createQuery(DbHistory.class);
        final var root = criteriaQuery.from(DbHistory.class);
        criteriaQuery.select(root);
        final var typedQuery = entityManager.createQuery(criteriaQuery).setFirstResult(offset);
        if (limit > 0) {
            typedQuery.setMaxResults(limit);
        }
        return typedQuery.getResultList();
    }
}