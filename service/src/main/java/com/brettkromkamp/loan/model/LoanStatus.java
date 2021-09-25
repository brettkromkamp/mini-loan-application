package com.brettkromkamp.loan.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LoanStatus {
    PENDING("Pending"),
    DENIED("Denied"),
    APPROVED("Approved");

    private final String name;

    private LoanStatus(String name) {
        this.name = name;
    }

    @Override
    @JsonValue
    public String toString() {
        return name;
    }
}
