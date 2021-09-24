package com.brettkromkamp.loan.repositories;

import com.brettkromkamp.loan.domains.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

}
