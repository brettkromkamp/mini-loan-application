import { Component, OnInit } from '@angular/core';
import { LoanService } from 'src/app/services/loan.service';

@Component({
	selector: 'app-loan-status',
	templateUrl: './loan-status.component.html',
	styleUrls: ['./loan-status.component.css']
})
export class LoanStatusComponent implements OnInit {

	currentLoan = null;
	loanStatus = '';
	loanIdentifier = '';

	constructor(private loanService: LoanService) { }

	ngOnInit(): void {
	}

	searchByIdentifier(): void {
		this.loanService.read(this.loanIdentifier)
			.subscribe(
				loan => {
					this.currentLoan = loan;
					this.loanStatus = loan.status;
					console.log(loan);
				},
				error => {
					this.currentLoan = null;
					console.log(error);
				}
			);
	}

}
