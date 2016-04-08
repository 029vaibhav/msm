package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Sales;
import com.mobiweb.msm.models.Technical;
import com.mobiweb.msm.models.enums.Status;
import com.mobiweb.msm.repositories.TechnicalRepo;
import com.mobiweb.msm.utils.Constants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TechnicalServiceImpl implements TechnicalService {

    @Autowired
    TechnicalRepo technicalRepo;


    @Override
    public void create(Technical technical) {

        try {
            technical.setCreated(DateTime.now(DateTimeZone.UTC));
            technical.setModified(DateTime.now().withZone(DateTimeZone.UTC));
            technicalRepo.save(technical);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void update(Technical technical) {

        Technical one = technicalRepo.findOne(technical.getId());
        if (one.getStatus() != null && one.getStatus() == Status.Done && technical.getStatus() == Status.Done) {
            technical.setUsername(one.getUsername());
        }
        if (technical.getStatus() == Status.Return || technical.getStatus() == Status.Returned || technical.getStatus() == Status.Delivered) {
            technical.setUsername(one.getUsername());
        }
        if (technical.getStatus() == Status.Returned || technical.getStatus() == Status.Return) {
            technical.setPrice(0);
        }
        technical.setModified(DateTime.now().withZone(DateTimeZone.UTC));
        technicalRepo.save(technical);
    }

    @Override
    public Technical retrieve(long id) {

        try {
            Technical Technical = technicalRepo.getOne(id);
            return Technical;

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Technical delete(long id) {

        try {
            Technical Technical = technicalRepo.getOne(id);
            technicalRepo.delete(id);
            return Technical;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Technical> serviceReport(String startDate, String endDate) {
        DateTime startDateTime = Constants.getShortDate(startDate);
        DateTime endDateDateTime = Constants.getShortDate(endDate);
        List<Technical> byDatesBetween = technicalRepo.findAllByCreatedBetween(startDateTime, endDateDateTime);
        return byDatesBetween;
    }

    @Override
    public List<Technical> serviceReportByStatusIn(String startDate, String endDate, List<Status> statuses) {

        DateTime startDateTime = Constants.getShortDate(startDate);
        DateTime endDateDateTime = Constants.getShortDate(endDate);
        List<Technical> byDatesBetween = technicalRepo.findAllByCreatedBetweenAndStatusIn(startDateTime, endDateDateTime, statuses);
        return byDatesBetween;
    }

    @Override
    public List<Sales> serviceReportByBrand(String startDate, String endDate) {
        DateTime startDateTime = Constants.getShortDate(startDate);
        DateTime endDateDateTime = Constants.getShortDate(endDate);
        List<Status> statusList = new ArrayList<>();
        statusList.add(Status.Done);
        statusList.add(Status.Delivered);

        List<Technical> sales = technicalRepo.findByCreatedBetweenAndStatusIn(startDateTime, endDateDateTime, statusList);
        List<Sales> returnSales = new ArrayList<>();
        Map<String, List<Technical>> collect1 = sales.stream().collect(Collectors.groupingBy(sales1 -> sales1.getBrand()));
        Iterator<Map.Entry<String, List<Technical>>> iterator = collect1.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<Technical>> next = iterator.next();

            Sales sales2 = new Sales();
            int price = 0;
            for (Technical sales1 : next.getValue()) {
                price += sales1.getPrice();
            }
            sales2.setBrand(next.getKey());
            sales2.setQuantity(next.getValue().size());
            sales2.setPrice(price);
            returnSales.add(sales2);
        }
        return returnSales;
    }

    @Override
    public List<Technical> ListForTechnicians() {

        List<Status> statusList = new ArrayList<>();
        statusList.add(Status.Done);
        statusList.add(Status.Return);
        statusList.add(Status.Pending);
        statusList.add(Status.Pna);
        statusList.add(Status.Processing);


        List<Technical> technicals = technicalRepo.findByStatusIn(statusList);
        return technicals;
    }

    @Override
    public List<Technical> servicesByUser(String startDate, String endDate, String username) {
        DateTime startDateTime = Constants.getShortDate(startDate);
        DateTime endDateDateTime = Constants.getShortDate(endDate);
        return technicalRepo.findAllByUsernameAndCreatedBetween(username, startDateTime, endDateDateTime);
    }

}
