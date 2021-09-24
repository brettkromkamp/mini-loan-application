package com.brettkromkamp.loan.service;

import com.brettkromkamp.loan.model.Borrower;

import java.util.Set;

public interface LoanService {
    public Set<Borrower> getBorrowersByLoanId(Long id);
}
