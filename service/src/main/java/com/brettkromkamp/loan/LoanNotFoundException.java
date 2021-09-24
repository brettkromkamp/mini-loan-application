package com.brettkromkamp.loan;

public class LoanNotFoundException extends RuntimeException {

    LoanNotFoundException() {
        super();
    }

    LoanNotFoundException(Long id) {
        super("Could not find loan: " + id);
    }
}
