    package com.loansRepayment.amortisationShd.domain;

    import org.springframework.data.repository.CrudRepository;

    import java.util.Optional;

    public interface LoanDetailsRepository extends CrudRepository<LoanDetails, Long>{
        Iterable<LoanDetails> findAll();
        Optional<LoanDetails> findById(Long id);
    }
