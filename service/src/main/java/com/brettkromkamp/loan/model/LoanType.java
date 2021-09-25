package com.brettkromkamp.loan.model;

/*
    Annuity loan means that you pay the same instalment amount, principal and interest, until the loan has been
    fully repaid (provided that the interest rate remains unchanged). As the loan is repaid, the principal
    payments constitute a larger share of the total amount.

    Serial loan means that you pay more at the start of the loan term and less at the end of the loan term. The
    instalment amounts therefore become smaller as the loan term progresses. As the loan is repaid, the principal
    payments constitute a larger share of the total amount.
 */

public enum LoanType {
    ANNUITY("annuity"),
    SERIAL("serial");

    private final String name;

    private LoanType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
