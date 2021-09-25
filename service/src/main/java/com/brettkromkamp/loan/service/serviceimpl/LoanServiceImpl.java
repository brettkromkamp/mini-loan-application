package com.brettkromkamp.loan.service.serviceimpl;

import com.brettkromkamp.loan.LoanNotFoundException;
import com.brettkromkamp.loan.model.Borrower;
import com.brettkromkamp.loan.model.Loan;
import com.brettkromkamp.loan.repository.LoanRepository;
import com.brettkromkamp.loan.service.LoanService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    @Transactional
    public Set<Borrower> getBorrowersByLoanId(Long id) {
        Optional<Loan> loan = loanRepository.findById(id);

        if (loan.isPresent()) {
            return loan.get().getBorrowers();
        } else {
            throw new LoanNotFoundException(id);
        }

    }
}
