package com.krzysztof.pawlak.models;

import com.krzysztof.pawlak.validation.ValidFromTo;

import javax.validation.constraints.Min;

@ValidFromTo
public class Range {

    @Min(1)
    private int from;

    @Min(0)
    private int to;

    public Range() {
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }
}