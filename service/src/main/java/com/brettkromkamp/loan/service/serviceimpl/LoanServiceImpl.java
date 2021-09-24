package com.brettkromkamp.loan.service.serviceimpl;

import com.brettkromkamp.loan.model.Borrower;
import com.brettkromkamp.loan.repository.LoanRepository;
import com.brettkromkamp.loan.service.LoanService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    LoanServiceImpl(LoanRepository loanRepository) {

        this.loanRepository = loanRepository;
    }

    @Override
    public Set<Borrower> getBorrowersByLoanId(Long id) {
        Set<Borrower> borrowers = loanRepository.findById(id).get().getBorrowers();
        return borrowers;
    }
}
