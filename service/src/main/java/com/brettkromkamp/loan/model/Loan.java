package com.brettkromkamp.loan.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "loans")
public class Loan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float amount;               // Lanebelop
    private String motivation;          // Behov
    private int duration;               // Lopetid
    private int deductionFreePeriod;    // Avdragsfri periode
    private LoanType type;              // Type

    private LoanStatus status;

    @OneToMany(mappedBy = "loan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Borrower> borrowers = new HashSet<>();

    public Loan() {

    }

    public Loan(float amount, String motivation, int duration, int deductionFreePeriod, LoanType type) {
        this(amount, motivation, duration, deductionFreePeriod, type, LoanStatus.PENDING);
    }

    public Loan(float amount, String motivation, int duration, int deductionFreePeriod, LoanType type, LoanStatus status) {
        this.amount = amount;
        this.motivation = motivation;
        this.duration = duration;
        this.deductionFreePeriod = deductionFreePeriod;
        this.type = type;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDeductionFreePeriod() {
        return deductionFreePeriod;
    }

    public void setDeductionFreePeriod(int deductionFreePeriod) {
        this.deductionFreePeriod = deductionFreePeriod;
    }

    public LoanType getType() {
        return type;
    }

    public void setType(LoanType type) {
        this.type = type;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Set<Borrower> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(Set<Borrower> borrowers) {
        this.borrowers = borrowers;

        for (Borrower borrower : borrowers) {
            borrower.setLoan(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
