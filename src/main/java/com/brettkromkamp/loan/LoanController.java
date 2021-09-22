package com.brettkromkamp.loan;

import com.brettkromkamp.loan.domains.Borrower;
import com.brettkromkamp.loan.domains.Loan;
import com.brettkromkamp.loan.repositories.LoanRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoanController {

    // Repository will be injected into the controller by the constructor
    private final LoanRepository loanRepository;

    LoanController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    // curl -v localhost:8080/loans
    @GetMapping("/loans")
    List<Loan> all() {
        List<Loan> loans = loanRepository.findAll();
        return loans;
    }

    /*
    curl -X POST localhost:8080/loans
         -H 'Content-type:application/json'
         -d '{"amount": "3250000", "motivation": "Vi vil kjøpe hus.", "duration": "240", "deductionFreePeriod": "12", "type": "annuitet"}'
     */
    @PostMapping("/loans")
    Loan create(@RequestBody Loan newLoan) {
        return loanRepository.save(newLoan);
    }

    // curl -v localhost:8080/loans/1
    @GetMapping("/loans/{id}")
    Loan read(@PathVariable Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(id));
    }

    @PutMapping("/loans/{id}")
    Loan update(@RequestBody Loan newLoan, @PathVariable Long id) {
        return loanRepository.findById(id)
                .map(loan -> {
                    loan.setAmount(newLoan.getAmount());
                    loan.setMotivation(newLoan.getMotivation());
                    loan.setDuration(newLoan.getDuration());
                    loan.setDeductionFreePeriod(newLoan.getDeductionFreePeriod());
                    loan.setType(newLoan.getType());
                    return loanRepository.save(loan);
                })
                .orElseGet(() -> {
                    newLoan.setId(id);
                    return loanRepository.save(newLoan);
                });
    }

    // curl -X DELETE localhost:8080/loans/3
    @DeleteMapping("/loans/{id}")
    void delete(@PathVariable Long id) {
        loanRepository.deleteById(id);
    }
}
