package com.krzysztof.pawlak.models.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DbHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String operation;

    public DbHistory(String operation) {
        this.operation = operation;
    }

    public DbHistory() {
    }

    public String getOperation() {
        return operation;
    }
}