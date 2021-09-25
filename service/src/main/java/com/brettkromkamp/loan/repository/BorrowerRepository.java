package com.brettkromkamp.loan.repository;

import com.brettkromkamp.loan.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    Borrower findBySocialSecurityNumber(String socialSecurityNumber);

    Borrower findByName(String name);
}
