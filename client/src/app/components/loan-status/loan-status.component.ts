import { Component, OnInit } from '@angular/core';
import { LoanService } from 'src/app/services/loan.service';

@Component({
  selector: 'app-loan-status',
  templateUrl: './loan-status.component.html',
  styleUrls: ['./loan-status.component.css']
})
export class LoanStatusComponent implements OnInit {

  currentLoan = null;
  loanIdentifier = '';

  constructor(private loanService: LoanService) { }

  ngOnInit(): void {
  }

  searchByIdentifier(): void {
    this.loanService.read(this.loanIdentifier)
      .subscribe(
        loan => {
          this.currentLoan = loan;
          console.log(loan);
        },
        error => {
          console.log(error);
        }
      );
  }

}
