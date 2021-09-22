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

import java.util.Set;
import java.util.prefs.BackingStoreException;

@Configuration
public class LoanApplicationDemo {

    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationDemo.class);

    @Bean
    public CommandLineRunner initialiseDatabase(LoanRepository loanRepository, BorrowerRepository borrowerRepository) {
        return args -> {
            // Create a new application for a loan
            Loan testLoan = new Loan(2_450_000.0f, "Vi skal låne penger til........", 300, 12, "annuitet");

            // Persist the loan to the repository
            loanRepository.save(testLoan);

            // Create and persist new borrowers
            borrowerRepository.save(new Borrower("01056000069", "Kari Nordmann", testLoan));
            borrowerRepository.save(new Borrower("01056000301", "Ola Nordmann", testLoan));

            System.out.println("Starting...");
            System.out.println();

            // Fetch all loans
            System.out.println("All loans:");
            System.out.println("----------------------------------------");
            for (Loan loan : loanRepository.findAll()) {
                System.out.println(loan.toString());
                Set<Borrower> borrowers = loan.getBorrowers();
                for (Borrower borrower : borrowers) {
                    System.out.println(borrower);
                }
            }
            System.out.println();

            // Fetch loan by identifier
            System.out.println("Loan with identifier '1L':");
            System.out.println("----------------------------------------");
            Loan loan = loanRepository.findById(1L).get();
            System.out.println(loan.toString());

            // Fetch all borrowers
            System.out.println("All borrowers:");
            System.out.println("----------------------------------------");
            for (Borrower borrower : borrowerRepository.findAll()) {
                System.out.println(borrower.toString());
            }
            System.out.println();

            // Fetch borrower by social security number
            System.out.println("Borrower with social security number '01056000301':");
            System.out.println("----------------------------------------");
            Borrower borrower = borrowerRepository.findBySocialSecurityNumber("01056000301");
            System.out.println(borrower.toString());
            System.out.println();

            System.out.println("Done!");
        };
    }
}
