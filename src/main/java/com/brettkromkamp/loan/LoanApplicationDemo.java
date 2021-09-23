package com.brettkromkamp.loan;

import com.brettkromkamp.loan.domains.Borrower;
import com.brettkromkamp.loan.domains.Loan;
import com.brettkromkamp.loan.repositories.BorrowerRepository;
import com.brettkromkamp.loan.repositories.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoanApplicationDemo {

    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationDemo.class);

    @Bean
    public CommandLineRunner initialiseDatabase(LoanRepository loanRepository, BorrowerRepository borrowerRepository) {
        return args -> {
            System.out.println("Starting...");
            // Create a new application for a loan
            Loan testLoan = new Loan(2_450_000.0f, "Vi skal låne penger til........", 300, 12, "annuitet");

            System.out.println("Persisting loan to the repository");
            loanRepository.save(testLoan);

            System.out.println("Persisting borrowers to the repository");
            borrowerRepository.save(new Borrower("01056000069", "Kari Nordmann", testLoan));
            borrowerRepository.save(new Borrower("01056000301", "Ola Nordmann", testLoan));
            System.out.println("Done!");
        };
    }
}
