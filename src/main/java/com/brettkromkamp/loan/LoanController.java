package com.brettkromkamp.loan;

import com.brettkromkamp.loan.domains.Borrower;
import com.brettkromkamp.loan.domains.Loan;
import com.brettkromkamp.loan.repositories.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    // Repository will be injected into the controller by the constructor
    private final LoanRepository loanRepository;

    // Assembler will be injected in the constructor
    private final LoanModelAssembler assembler;

    LoanController(LoanRepository loanRepository, LoanModelAssembler assembler) {
        this.loanRepository = loanRepository;
        this.assembler = assembler;
    }

    // curl -v localhost:8080/api/v1/loans | json_pp
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
         -d '{"amount": "3250000", "motivation": "Vi vil kjøpe hus.", "duration": "240", "deductionFreePeriod": "12", "type": "annuitet", "borrowers": [{"name": "Cecilie Johansen", "socialSecurityNumber": "01056000307"}, {"name": "Tommy Johansen", "socialSecurityNumber": "01056000311"}]}'
     */
    @PostMapping
    ResponseEntity<?> create(@RequestBody Loan newLoan) {
        logger.info("A request for a new loan has been made");
        EntityModel<Loan> entityModel = assembler.toModel(loanRepository.save(newLoan));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // curl -v localhost:8080/api/v1/loans/1 | json_pp
    @GetMapping("/{id}")
    EntityModel<Loan> read(@PathVariable Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(id));
        return assembler.toModel(loan);
    }

    @PutMapping("/{id}")
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

    // curl -v -X DELETE localhost:8080/api/v1/loans/1 | json_pp
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        loanRepository.deleteById(id);
    }

    // curl -v -X GET localhost:8080/api/v1/loans/1/borrowers | json_pp
    @GetMapping("/{id}/borrowers")
    Set<Borrower> borrowers(@PathVariable Long id) {
        Set<Borrower> borrowers = loanRepository.findById(id).get().getBorrowers();
        return borrowers;
    }
}
