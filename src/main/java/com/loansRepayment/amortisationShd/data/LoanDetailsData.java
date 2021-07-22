    package com.loansRepayment.amortisationShd.data;

    import lombok.Data;

    import java.math.BigDecimal;

    @Data
    public class LoanDetailsData {
        private BigDecimal costOfAsset;
        private BigDecimal depositPaid;
        private double yearlyInterest;
        private Integer numberOfMonthlyPayments;
        private BigDecimal  ballonPayment;
    }
