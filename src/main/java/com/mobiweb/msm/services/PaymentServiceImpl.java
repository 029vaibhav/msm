package com.mobiweb.msm.services;

import com.mobiweb.msm.models.DealerInfo;
import com.mobiweb.msm.models.Payment;
import com.mobiweb.msm.repositories.PaymentRepo;
import com.mobiweb.msm.utils.Constants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepo paymentRepo;
    @Autowired
    DealerInfoService dealerInfoService;


    @Override
    public Payment create(Payment payment) {

        try {
            payment.setCreated(DateTime.now());
            payment.setModified(DateTime.now());
            Payment save = paymentRepo.save(payment);
            int sum = getPurchaseAmount();
            updateDealerInfo(sum, payment.getDealerId());
            return save;
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public void update(Payment payment) {
        try {
            payment.setModified(DateTime.now());
            paymentRepo.save(payment);
            int sum = getPurchaseAmount();
            updateDealerInfo(sum, payment.getDealerId());
        } catch (Exception e) {
        }
    }

    @Override
    public Payment retrieve(long id) {

        try {
            Payment Payment = paymentRepo.getOne(id);
            return Payment;

        } catch (Exception e) {
            throw e;
        }

    }

    private void updateDealerInfo(int sum, Long id) {

        DealerInfo retrieve = dealerInfoService.retrieve(id);
        retrieve.setPayedAmount(sum);
        retrieve.setModified(DateTime.now().withZone(DateTimeZone.UTC));
        dealerInfoService.update(retrieve);
    }

    public int getPurchaseAmount() {
        List<Payment> all = paymentRepo.findAll();
        int sum = all.stream().mapToInt(Payment::getAmount).sum();
        return sum;
    }


    @Override
    public Payment delete(long id) {

        try {
            Payment payment = paymentRepo.getOne(id);
            paymentRepo.delete(id);
            int sum = getPurchaseAmount();
            updateDealerInfo(sum, payment.getDealerId());
            return payment;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Payment> findAllByDealerIdAndCreatedDates(Long dealerID, String startDate, String endDate) {

        DateTime startDateTime = Constants.getDate(startDate);
        DateTime endDateTime = Constants.getDate(endDate);
        return paymentRepo.findAllByCreatedBetweenAndDealerId(startDateTime, endDateTime, dealerID);
    }

    @Override
    public List<Payment> findAllByDealerIdAndCreatedAfterDates(Long dealerID, String startDate) {

        DateTime startDateTime = Constants.getDate(startDate);
        return paymentRepo.findAllByCreatedAfterAndDealerId(startDateTime, dealerID);
    }
}
