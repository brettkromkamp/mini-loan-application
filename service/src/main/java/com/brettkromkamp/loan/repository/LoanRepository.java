package com.brettkromkamp.loan.repository;

import com.brettkromkamp.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

}
