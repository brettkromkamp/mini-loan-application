package com.brettkromkamp.loan;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException() {
        super();
    }

    public LoanNotFoundException(Long id) {
        super("Could not find loan: " + id);
    }
}
