package com.brettkromkamp.loan.repository;

import com.brettkromkamp.loan.model.Loan;
import com.brettkromkamp.loan.model.LoanStatus;
import com.brettkromkamp.loan.model.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByType(LoanType type);
}
