package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Payment;

import java.util.List;

public interface PaymentService {

    Payment create(Payment Payment);

    void update(Payment Payment);

    Payment retrieve(long id);

    Payment delete(long id);

    List<Payment> findAllByDealerIdAndCreatedDates(Long dealerID,String startDate,String endDate);
    List<Payment> findAllByDealerIdAndCreatedAfterDates(Long dealerID,String startDate);


}
