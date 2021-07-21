package com.loansRepayment.amortisationShd;

import com.loansRepayment.amortisationShd.domain.LoanDetails;
import com.loansRepayment.amortisationShd.domain.LoanDetailsRepository;
import com.loansRepayment.amortisationShd.domain.ScheduleRepository;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
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

}
