package com.brettkromkamp.loan.controller;

import com.brettkromkamp.loan.model.Loan;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
class LoanModelAssembler implements RepresentationModelAssembler<Loan, EntityModel<Loan>> {

    @Override
    public EntityModel<Loan> toModel(Loan loan) {
        return EntityModel.of(loan,
                linkTo(methodOn(LoanController.class).read(loan.getId())).withSelfRel(),
                linkTo(methodOn(LoanController.class).list()).withRel("loans"));
    }
}
