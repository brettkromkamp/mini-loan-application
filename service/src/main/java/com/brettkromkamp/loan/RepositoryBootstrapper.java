package com.brettkromkamp.loan;

import com.brettkromkamp.loan.model.Borrower;
import com.brettkromkamp.loan.model.Loan;
import com.brettkromkamp.loan.model.LoanStatus;
import com.brettkromkamp.loan.model.LoanType;
import com.brettkromkamp.loan.repository.BorrowerRepository;
import com.brettkromkamp.loan.repository.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryBootstrapper {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryBootstrapper.class);

    @Bean
    public CommandLineRunner initialiseDatabase(LoanRepository loanRepository, BorrowerRepository borrowerRepository) {
        return args -> {
            // Create a new application for a loan
            Loan testLoan = new Loan(2_450_000.0f, "Vi skal låne penger til........", 300, 12, LoanType.ANNUITY, LoanStatus.PENDING);

            logger.info("Persisting a test loan to the repository");
            loanRepository.save(testLoan);

            logger.info("Persisting test borrowers to the repository");
            borrowerRepository.save(new Borrower("01056000069", "Kari Nordmann", testLoan));
            borrowerRepository.save(new Borrower("01056000301", "Ola Nordmann", testLoan));
        };
    }
}
