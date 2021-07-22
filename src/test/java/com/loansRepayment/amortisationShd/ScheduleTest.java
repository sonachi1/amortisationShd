package com.loansRepayment.amortisationShd;

import com.loansRepayment.amortisationShd.domain.LoanDetails;
import com.loansRepayment.amortisationShd.domain.LoanDetailsRepository;
import com.loansRepayment.amortisationShd.domain.Schedule;
import com.loansRepayment.amortisationShd.domain.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@WebMvcTest(ScheduleController.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanDetailsRepository loanDetailsRepository;
    @MockBean
    private ScheduleRepository scheduleRepository;

    @Test
    public void testGetALoanDetailsData() throws Exception {
        List<LoanDetails> listLoanDetails = new ArrayList<>();
        LoanDetails loanDetails = new LoanDetails();
        loanDetails.setId(1L);
        loanDetails.setCostOfAsset(new BigDecimal(10000));
        loanDetails.setDepositPaid(new BigDecimal(200));
        loanDetails.setNumberOfMonthlyPayments(6);
        loanDetails.setYearlyInterest(2.5);
        loanDetails.setDepositPaid(new BigDecimal(200));
        listLoanDetails.add(loanDetails);

        // when(loanDetailsRepository.findById(anyLong())).thenReturn(listLoanDetails);
        when(loanDetailsRepository.findById(1L)).thenReturn(java.util.Optional.of(loanDetails));
        mockMvc.perform(MockMvcRequestBuilders.get("/loan/load_detail/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yearlyInterest").value(2.5));
    }

    @Test
    public void testGetAllLoanDetailsData() throws Exception {

        List<LoanDetails> listLoanDetails = new ArrayList<>();
        LoanDetails loanDetails = new LoanDetails();
        loanDetails.setId(1L);
        loanDetails.setCostOfAsset(new BigDecimal(10000));
        loanDetails.setDepositPaid(new BigDecimal(200));
        loanDetails.setNumberOfMonthlyPayments(6);
        loanDetails.setYearlyInterest(2.5);
        loanDetails.setDepositPaid(new BigDecimal(200));
        listLoanDetails.add(loanDetails);

        LoanDetails loanDetails_second = new LoanDetails();
        loanDetails_second.setId(2L);
        loanDetails_second.setCostOfAsset(new BigDecimal(10000));
        loanDetails_second.setDepositPaid(new BigDecimal(200));
        loanDetails_second.setNumberOfMonthlyPayments(6);
        loanDetails_second.setYearlyInterest(2.5);
        loanDetails_second.setDepositPaid(new BigDecimal(200));
        listLoanDetails.add(loanDetails_second);

        when(loanDetailsRepository.findAll()).thenReturn(listLoanDetails);
        mockMvc.perform(MockMvcRequestBuilders.get("/loan/load_details").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testSchedules() throws Exception {

        LoanDetails loanDetails = new LoanDetails();
        loanDetails.setId(1L);
        loanDetails.setCostOfAsset(new BigDecimal(10000));
        loanDetails.setDepositPaid(new BigDecimal(200));
        loanDetails.setNumberOfMonthlyPayments(6);
        loanDetails.setYearlyInterest(2.5);
        loanDetails.setBalloonPayment(new BigDecimal(0));
        loanDetails.setDepositPaid(new BigDecimal(200));


        List<Schedule> listSchedule = new ArrayList<>();
        Schedule schedule = new Schedule();
        schedule.setLoanDetails(loanDetails);
        schedule.setPayment(new BigDecimal(200));
        schedule.setBalance(new BigDecimal(0));
        schedule.setPrinicipal(new BigDecimal(0));
        schedule.setInterest(new BigDecimal(5.345));
        schedule.setId(1L);
        schedule.setPeriod(6);
        listSchedule.add(schedule);

        Schedule scheduleAnother = new Schedule();
        scheduleAnother.setLoanDetails(loanDetails);
        scheduleAnother.setPayment(new BigDecimal(200));
        scheduleAnother.setBalance(new BigDecimal(0));
        scheduleAnother.setPrinicipal(new BigDecimal(0));
        scheduleAnother.setInterest(new BigDecimal(5.345));
        scheduleAnother.setId(2L);
        scheduleAnother.setPeriod(7);
        listSchedule.add(scheduleAnother);


        when(scheduleRepository.findAll()).thenReturn(listSchedule);
        mockMvc.perform(MockMvcRequestBuilders.get("/loan/schedules").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void testSchedulesNotFount() throws Exception {

        LoanDetails loanDetails = new LoanDetails();
        loanDetails.setId(1L);
        loanDetails.setCostOfAsset(new BigDecimal(10000));
        loanDetails.setDepositPaid(new BigDecimal(200));
        loanDetails.setNumberOfMonthlyPayments(6);
        loanDetails.setYearlyInterest(2.5);
        loanDetails.setBalloonPayment(new BigDecimal(0));
        loanDetails.setDepositPaid(new BigDecimal(200));


        List<Schedule> listSchedule = new ArrayList<>();
        Schedule schedule = new Schedule();
        schedule.setLoanDetails(loanDetails);
        schedule.setPayment(new BigDecimal(200));
        schedule.setBalance(new BigDecimal(0));
        schedule.setPrinicipal(new BigDecimal(0));
        schedule.setInterest(new BigDecimal(5.345));
        schedule.setId(1L);
        schedule.setPeriod(6);
        listSchedule.add(schedule);

        Schedule scheduleAnother = new Schedule();
        scheduleAnother.setLoanDetails(loanDetails);
        scheduleAnother.setPayment(new BigDecimal(200));
        scheduleAnother.setBalance(new BigDecimal(0));
        scheduleAnother.setPrinicipal(new BigDecimal(0));
        scheduleAnother.setInterest(new BigDecimal(5.345));
        scheduleAnother.setId(2L);
        scheduleAnother.setPeriod(7);
        listSchedule.add(scheduleAnother);


        when(scheduleRepository.findAll()).thenReturn(listSchedule);
        mockMvc.perform(MockMvcRequestBuilders.get("/loan/schedule").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
