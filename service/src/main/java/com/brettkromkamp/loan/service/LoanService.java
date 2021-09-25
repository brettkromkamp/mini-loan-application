package com.brettkromkamp.loan.service;

import com.brettkromkamp.loan.model.Borrower;
import com.brettkromkamp.loan.model.LoanStatus;

import java.util.Set;

public interface LoanService {
    Set<Borrower> getBorrowersByLoanId(Long id);

    void setStatus(Long id, LoanStatus status);
}
