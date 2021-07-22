package com.loansRepayment.amortisationShd.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class LoanDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private BigDecimal costOfAsset;
    private BigDecimal depositPaid;
    private double yearlyInterest;
    private Integer numberOfMonthlyPayments;
    private BigDecimal balloonPayment;


    @OneToMany(
            mappedBy = "loanDetails",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Schedule> schedule = new ArrayList<>();


    public LoanDetails() {
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getCostOfAsset() {
        return costOfAsset;
    }

    public void setCostOfAsset(BigDecimal costOfAsset) {
        this.costOfAsset = costOfAsset;
    }

    public BigDecimal getDepositPaid() {
        return depositPaid;
    }

    public void setDepositPaid(BigDecimal depositPaid) {
        this.depositPaid = depositPaid;
    }

    public double getYearlyInterest() {
        return yearlyInterest;
    }

    public void setYearlyInterest(double yearlyInterest) {
        this.yearlyInterest = yearlyInterest;
    }

    public Integer getNumberOfMonthlyPayments() {
        return numberOfMonthlyPayments;
    }

    public void setNumberOfMonthlyPayments(Integer numberOfMonthlyPayments) {
        this.numberOfMonthlyPayments = numberOfMonthlyPayments;
    }

    public BigDecimal getBalloonPayment() {
        return balloonPayment;
    }

    public void setBalloonPayment(BigDecimal balloonPayment) {
        this.balloonPayment = balloonPayment;
    }
}
