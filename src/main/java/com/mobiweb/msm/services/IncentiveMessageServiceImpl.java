package com.mobiweb.msm.services;

import com.mobiweb.msm.exceptions.DuplicateIncentive;
import com.mobiweb.msm.exceptions.error.ErrorMessage;
import com.mobiweb.msm.models.IncentiveMessage;
import com.mobiweb.msm.models.Message;
import com.mobiweb.msm.models.Sales;
import com.mobiweb.msm.models.enums.Role;
import com.mobiweb.msm.repositories.IncentiveMessageRepo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncentiveMessageServiceImpl implements IncentiveMessageService {

    @Autowired
    IncentiveMessageRepo incentiveMessageRepo;
    @Autowired
    MessageService messageService;
    @Autowired
    SalesService salesService;


    @Override
    public void create(IncentiveMessage incentiveMessage) {

        List<IncentiveMessage> allByBrandAndModel = incentiveMessageRepo.getAllByBrandAndModel(incentiveMessage.getBrand(), incentiveMessage.getModel());
        if (allByBrandAndModel != null && allByBrandAndModel.size() == 0) {
            incentiveMessage.setCreated(DateTime.now().withZone(DateTimeZone.UTC));
            incentiveMessage.setModified(DateTime.now().withZone(DateTimeZone.UTC));
            incentiveMessageRepo.save(incentiveMessage);
            String messageString = "sell " + incentiveMessage.getQuantity() + " " + incentiveMessage.getBrand() + " " + incentiveMessage.getModel() + " at Rs " + incentiveMessage.getBasePrice() + " and get Rs " + incentiveMessage.getIncentiveAmount();
            Message message = new Message();
            message.setMessage(messageString);
            message.setRole(Role.Salesman);
            message.setEndDate(incentiveMessage.getValidity());
            messageService.create(message);
        } else {
            throw new DuplicateIncentive(ErrorMessage.DUPLICATE_INCENTIVE);
        }
    }

    @Override
    public void update(IncentiveMessage IncentiveMessage) {

    }

    @Override
    public IncentiveMessage retrieve(long id) {

        try {
            IncentiveMessage IncentiveMessage = incentiveMessageRepo.getOne(id);
            return IncentiveMessage;

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public IncentiveMessage delete(long id) {

        try {
            IncentiveMessage IncentiveMessage = incentiveMessageRepo.getOne(id);
            incentiveMessageRepo.delete(id);
            return IncentiveMessage;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<IncentiveMessage> getAll() {
        List<IncentiveMessage> all = incentiveMessageRepo.findAll();
        return all;

    }

    @Override
    public List<Sales> getIncentiveQualifiedUsers(long incentiveID) {

        IncentiveMessage incentiveMessage = incentiveMessageRepo.getOne(incentiveID);
        if (incentiveMessage != null) {
            String brand = incentiveMessage.getBrand();
            String model = incentiveMessage.getModel();
            DateTime validity = incentiveMessage.getValidity().toDateTime(DateTimeZone.UTC);
            DateTime created = incentiveMessage.getCreated().toDateTime(DateTimeZone.UTC);
            List<Sales> sales = salesService.salesOfMobileByBrandModelDate(brand, model, created, validity);
            return sales;
        }
        return null;
    }


}
