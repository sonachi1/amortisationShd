package com.loansRepayment.amortisationShd.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    Iterable<Schedule> findAll();

    Optional<Schedule> findById(Long id);
}
