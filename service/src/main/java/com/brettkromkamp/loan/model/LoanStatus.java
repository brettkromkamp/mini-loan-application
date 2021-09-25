package com.brettkromkamp.loan.model;

public enum LoanStatus {
    PENDING("Pending"),
    DENIED("Denied"),
    APPROVED("Approved");

    private final String name;

    private LoanStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
