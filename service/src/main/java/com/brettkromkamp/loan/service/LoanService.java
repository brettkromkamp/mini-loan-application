package com.brettkromkamp.loan.service;

import com.brettkromkamp.loan.model.Borrower;

import java.util.Set;

public interface LoanService {
    Set<Borrower> getBorrowersByLoanId(Long id);
}
