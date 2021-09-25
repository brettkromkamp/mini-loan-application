package com.brettkromkamp.loan.controller;

import com.brettkromkamp.loan.LoanNotFoundException;
import com.brettkromkamp.loan.model.Borrower;
import com.brettkromkamp.loan.model.Loan;
import com.brettkromkamp.loan.model.LoanStatus;
import com.brettkromkamp.loan.repository.LoanRepository;
import com.brettkromkamp.loan.service.serviceimpl.LoanServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
    private final LoanRepository loanRepository;
    private final LoanModelAssembler assembler;

    private final LoanServiceImpl loanService;

    LoanController(LoanRepository loanRepository, LoanModelAssembler assembler, LoanServiceImpl loanService) {
        this.loanRepository = loanRepository;
        this.assembler = assembler;
        this.loanService = loanService;
    }

    // curl -v localhost:8080/api/v1/loans
    @GetMapping
    CollectionModel<EntityModel<Loan>> list() {
        List<EntityModel<Loan>> loans = loanRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(loans,
                linkTo(methodOn(LoanController.class).list()).withSelfRel());
    }

    /*
        curl -v -X POST localhost:8080/api/v1/loans
             -H 'Content-type:application/json'
             -d '{"amount": "3250000", "motivation": "Vi ønsker å kjøpe drømmehuset vårt.", "duration": "240", "deductionFreePeriod": "12", "type": "ANNUITY", "status": "PENDING", "borrowers": [{"name": "Cecilie Johansen", "socialSecurityNumber": "01056000307"}, {"name": "Tommy Johansen", "socialSecurityNumber": "01056000311"}]}'
     */
    @PostMapping
    ResponseEntity<?> create(@RequestBody @NotNull Loan newLoan) {
        logger.info(MessageFormat.format("An application for a new loan has been made. Amount: {0}, Duration: {1}, Type: {2}",
                newLoan.getAmount(), newLoan.getDuration(), newLoan.getType()));
        if (newLoan.getStatus() == null) {
            newLoan.setStatus(LoanStatus.PENDING);
        }
        EntityModel<Loan> entityModel = assembler.toModel(loanRepository.save(newLoan));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // curl -v localhost:8080/api/v1/loans/1
    @GetMapping("/{id}")
    EntityModel<Loan> read(@PathVariable Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(id));
        return assembler.toModel(loan);
    }

    /*
        curl -v -X PUT localhost:8080/api/v1/loans/2
             -H 'Content-type:application/json'
             -d '{"amount": "3250000", "motivation": "Vi ønsker å kjøpe drømmehuset vårt.", "duration": "240", "deductionFreePeriod": "12", "type": "ANNUITY", "status": "APPROVED", "borrowers": [{"name": "Cecilie Johansen", "socialSecurityNumber": "01056000307"}, {"name": "Tommy Johansen", "socialSecurityNumber": "01056000311"}]}'
     */
    @PutMapping("/{id}")
    Loan update(@RequestBody @NotNull Loan newLoan, @PathVariable Long id) {
        return loanRepository.findById(id)
                .map(loan -> {
                    loan.setAmount(newLoan.getAmount());
                    loan.setMotivation(newLoan.getMotivation());
                    loan.setDuration(newLoan.getDuration());
                    loan.setDeductionFreePeriod(newLoan.getDeductionFreePeriod());
                    loan.setType(newLoan.getType());
                    loan.setStatus(newLoan.getStatus());
                    return loanRepository.save(loan);
                })
                .orElseGet(() -> {
                    newLoan.setId(id);
                    return loanRepository.save(newLoan);
                });
    }

    // curl -v -X DELETE localhost:8080/api/v1/loans/1
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        try {
            loanRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new LoanNotFoundException(id);
        }
    }

    // curl -v localhost:8080/api/v1/loans/1/borrowers
    @GetMapping("/{id}/borrowers")
    Set<Borrower> borrowers(@PathVariable Long id) {
        Set<Borrower> borrowers = loanService.getBorrowersByLoanId(id);
        return borrowers;
    }

    /*
    // curl -v localhost:8080/api/v1/loans/1/status
    @GetMapping("/{id}/status")
    Map<String, String> getStatus(@PathVariable Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(id));
        Map<String, String> map = new HashMap<>();
        map.put("identifier", loan.getId().toString());
        map.put("status", loan.getStatus().name());
        return map;
    }
     */
}
