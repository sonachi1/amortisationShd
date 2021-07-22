package com.loansRepayment.amortisationShd.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int period;
    private BigDecimal payment;
    private BigDecimal prinicipal;
    private BigDecimal interest;
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "loanDetail_id")
    private LoanDetails loanDetails;

    public Schedule(int period, BigDecimal payment, BigDecimal prinicipal, BigDecimal interest, BigDecimal balance, LoanDetails loanDetails) {
        this.period = period;
        this.payment = payment;
        this.prinicipal = prinicipal;
        this.interest = interest;
        this.balance = balance;
        this.loanDetails = loanDetails;
    }

    public Schedule() {

    }
    //  private long loadDetailId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getPrinicipal() {
        return prinicipal;
    }

    public void setPrinicipal(BigDecimal prinicipal) {
        this.prinicipal = prinicipal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LoanDetails getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
    }
}
