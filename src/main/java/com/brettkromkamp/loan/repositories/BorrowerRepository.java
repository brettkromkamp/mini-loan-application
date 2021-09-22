package com.brettkromkamp.loan.repositories;

import com.brettkromkamp.loan.domains.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    Borrower findBySocialSecurityNumber(String socialSecurityNumber);
}
