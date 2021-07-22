    package com.loansRepayment.amortisationShd;


    import com.loansRepayment.amortisationShd.data.LoanDetailsData;
    import com.loansRepayment.amortisationShd.domain.*;
    import org.springframework.beans.BeanUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


    import java.net.URI;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping(path = "/loan")
    public class ScheduleController {
        @Autowired
        private LoanDetailsRepository loanRepository;

        @Autowired
        private ScheduleRepository scheduleRepository;

        @Autowired
        private ScheduleService  scheduleService ;


        private LoanDetails loanDetails = new LoanDetails() ;

        private Schedule schedule = new Schedule();




        @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
        public ResponseEntity<Object> newLoan(@RequestBody LoanDetailsData loanDetailsData) {

            if (scheduleService.CalculateSchedule(loanDetailsData)) {
                System.out.println("loans calculated and schedules created");
            }else{
                System.out.println("validation failed");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(loanDetails.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }

        @GetMapping("/load_details")
        public List<LoanDetails> getAllLoanDetailsData() {
            List<LoanDetails> loanResponse = (List<LoanDetails>) loanRepository.findAll();
            return loanResponse;

        }

        @GetMapping("/schedules")
        public List<Schedule> getAllScheduleData() {
            List<Schedule> scheduleResponse = (List<Schedule>) scheduleRepository.findAll();
            return scheduleResponse;

        }

        @GetMapping("/load_detail/{id}")
        public ResponseEntity<LoanDetails>getAllLoanDetailsDataById(@PathVariable("id") Long id)  {
            Optional <LoanDetails>loanDetail =  loanRepository.findById(id);
            return ResponseEntity.of(loanDetail);
        }


        @GetMapping("/schedule/{id}")
        public ResponseEntity<Schedule> getAllScheduleDataByID(@PathVariable("id") Long id){
            Optional <Schedule> schedule = scheduleRepository.findById(id);
            return ResponseEntity.of(schedule);

        }

    }
