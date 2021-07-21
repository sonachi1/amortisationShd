package com.loansRepayment.amortisationShd.domain;

import com.loansRepayment.amortisationShd.data.LoanDetailsData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Service
public class ScheduleService {

    @Autowired
    private LoanDetailsRepository loanRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;




    public boolean CalculateSchedule(LoanDetailsData loanDetailsData) {

        boolean pass = true;

        BigDecimal p;
        BigDecimal r;
        BigDecimal b;
        BigDecimal i;
        int n;

        BigDecimal monthlyRepayment;
        BigDecimal mRNumerator = new BigDecimal(0);
        BigDecimal mRDenominator = new BigDecimal(1);

        BigDecimal r_plus_1;
        BigDecimal r_plus_1_pow_negate_n;

        int period;
        BigDecimal payment;
        BigDecimal prinicipal;
        BigDecimal interest;
        BigDecimal balance;



        //Monthly Repayment = (P - (B / ((1 + r) ^ n))) * (r / (1 - (1 + r) ^ -n)) - ballon
        //monthlyRepayment = p * ((r * (1+r) ^ n) / ((1 + r) ^ n - 1));

        try {

            if (validate(loanDetailsData)) {
                p = loanDetailsData.getCostOfAsset().subtract(loanDetailsData.getDepositPaid());
                r = BigDecimal.valueOf(loanDetailsData.getYearlyInterest()).divide(new BigDecimal(100));

                n = loanDetailsData.getNumberOfMonthlyPayments().intValue();
                r = r.divide(BigDecimal.valueOf(n), 5, RoundingMode.HALF_UP);
                if (loanDetailsData.getBallonPayment() == null) {
                    loanDetailsData.setBallonPayment(BigDecimal.ZERO);
                }
                b = loanDetailsData.getBallonPayment();

                r_plus_1 = r.add(new BigDecimal(1));

                if (b.compareTo(new BigDecimal(0)) == 0) {
                    /*
                     * calculate amortisation schedule for a provided set of loan details ( with a balloon payment)
                     */
                    r_plus_1 = r_plus_1.pow(n, new MathContext(5)).setScale(5, RoundingMode.HALF_UP); //raised to the power of n
                    mRDenominator = r_plus_1.subtract(new BigDecimal(1)).setScale(5, RoundingMode.HALF_UP); //Subtract 1
                    mRNumerator = r_plus_1.multiply(r, new MathContext(5)).setScale(5, RoundingMode.HALF_UP); //multiply r
                    BigDecimal monthlyRepayment_temp = mRNumerator.divide(mRDenominator, 5, RoundingMode.HALF_DOWN).setScale(5, RoundingMode.HALF_UP);
                    monthlyRepayment = p.multiply(monthlyRepayment_temp).setScale(2, RoundingMode.HALF_UP);
                } else {
                    /*
                     * calculate amortisation schedule for a provided set of loan details ( without a balloon payment)
                     */
                    double d_r_plus_1_pow_negate_n = r_plus_1.doubleValue();
                    double dmRDenominator = Math.pow(d_r_plus_1_pow_negate_n, -n);
                    r_plus_1_pow_negate_n = BigDecimal.valueOf(dmRDenominator);
                    mRDenominator = new BigDecimal(1).subtract(r_plus_1_pow_negate_n).setScale(5, RoundingMode.HALF_UP);
                    mRDenominator = r.divide(mRDenominator, 5, RoundingMode.HALF_UP).setScale(5, RoundingMode.HALF_UP);
                    r_plus_1 = r_plus_1.pow(n, new MathContext(5)).setScale(5, RoundingMode.HALF_UP); //raised to the power of n
                    mRNumerator = b.divide(r_plus_1, 5, RoundingMode.HALF_UP).setScale(5, RoundingMode.HALF_UP);
                    mRNumerator = p.subtract(mRNumerator).setScale(5, RoundingMode.HALF_UP);
                    monthlyRepayment = mRNumerator.multiply(mRDenominator, new MathContext(2));

                }
                monthlyRepayment = monthlyRepayment.setScale(2, RoundingMode.HALF_UP);
                i = p.multiply(r, new MathContext(5)).setScale(5);
                prinicipal = monthlyRepayment.subtract(i);
                interest = i.setScale(2, RoundingMode.HALF_UP);
                period = 1;
                balance = p.subtract(prinicipal);

                /*
                 * save loan details to table
                 */
                LoanDetails loanDetails = new LoanDetails();
                BeanUtils.copyProperties(loanDetailsData, loanDetails, "id");
                loanRepository.save(loanDetails);

                /*
                 * create schedules and save to database.
                 */
                for (int j = 0; j < n; j++) {
                    Schedule schedule = new Schedule();
                    schedule.setInterest(interest);
                    schedule.setPrinicipal(prinicipal);
                    schedule.setPeriod(period);
                    schedule.setBalance(balance);
                    schedule.setPayment(monthlyRepayment);
                    schedule.setLoanDetails(loanDetails);
                    scheduleRepository.save(schedule);
                    interest = balance.multiply(r, new MathContext(5)).setScale(5);
                    period = period + 1;
                    prinicipal = monthlyRepayment.subtract(interest);
                    balance = balance.subtract(prinicipal);
                    if (balance.compareTo(BigDecimal.ZERO) == -1) {
                        balance = BigDecimal.ZERO;
                    }

                }
            } else {
                pass = false;
            }
        }catch(ArithmeticException e) {
            System.out.println(e);
        }catch(NumberFormatException nfe) {
            System.out.println(nfe);
        }
        return pass;
    }

    private boolean validate(LoanDetailsData loanDetailsData){
        boolean pass =true;

        /*
         * validates the data
         * */
        if (loanDetailsData.getCostOfAsset()==null || loanDetailsData.getDepositPaid()==null|| loanDetailsData.getYearlyInterest()==0 ||loanDetailsData.getNumberOfMonthlyPayments()==null){
            pass = false;
        }
        return pass;
    }

}
