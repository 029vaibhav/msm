package com.mobiweb.msm.repositories;

import com.mobiweb.msm.models.Payment;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {


    List<Payment> findAllByCreatedBetweenAndDealerId(DateTime from, DateTime to, Long dealearId);

    List<Payment> findAllByCreatedAfterAndDealerId(DateTime from, Long dealearId);

    List<Payment> findAllByDealerId(Long id);
}
