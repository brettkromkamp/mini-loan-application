import { Component, OnInit } from '@angular/core';
import { LoanService } from 'src/app/services/loan.service';

@Component({
  selector: 'app-loan-create',
  templateUrl: './loan-create.component.html',
  styleUrls: ['./loan-create.component.css']
})
export class LoanCreateComponent implements OnInit {
  loan = {
    amount: 0,
    motivation: '',
    duration: 0,
    deductionFreePeriod: 0,
    type: 'annuity',
    borrowers: [
      { name: '', socialSecurityNumber: '' }, { name: '', socialSecurityNumber: '' }
    ]
  }
  submitted = false;

  constructor(private loanService: LoanService) { }

  ngOnInit(): void {
  }

  createLoan(): void {
    const data = {
      amount: this.loan.amount,
      motivation: this.loan.motivation,
      duration: this.loan.duration,
      deductionFreePeriod: this.loan.deductionFreePeriod,
      type: this.loan.type,
      borrowers: this.loan.borrowers
    };

    this.loanService.create(data)
      .subscribe(
        response => {
          console.log(response);
          this.submitted = true;
        },
        error => {
          console.log(error);
        }
      );
  }

  newLoan(): void {
    this.submitted = false;
    this.loan = {
      amount: 0,
      motivation: '',
      duration: 0,
      deductionFreePeriod: 0,
      type: 'annuity',
      borrowers: [
        { name: '', socialSecurityNumber: '' }, { name: '', socialSecurityNumber: '' }
      ]
    };
  }

}
