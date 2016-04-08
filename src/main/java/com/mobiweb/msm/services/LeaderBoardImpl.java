package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Leader;
import com.mobiweb.msm.models.Sales;
import com.mobiweb.msm.models.Technical;
import com.mobiweb.msm.models.User;
import com.mobiweb.msm.models.enums.Status;
import com.mobiweb.msm.repositories.UserRepo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service("ReportService")
public class LeaderBoardImpl implements ReportService {

    @Autowired
    TechnicalService technicalService;
    @Autowired
    SalesService sales;
    @Autowired
    UserRepo userRepo;
    @Autowired
    IncentiveMessageService incentiveMessageService;

    @Override
    public List<Leader> getLeaderBoard(String startDate, String endDate) {


        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Leader> leaders = new ArrayList<>();
        List<Callable<List<Leader>>> callableList = new ArrayList<>();

        callableList.add(() -> getSalesInformation(startDate, endDate));
        callableList.add(() -> getTechnicalInformation(startDate, endDate));

        try {
            List<Future<List<Leader>>> futures = executorService.invokeAll(callableList);
            Future<List<Leader>> listFuture = futures.get(0);
            leaders.addAll(listFuture.get());
            Future<List<Leader>> listFuture1 = futures.get(1);
            leaders.addAll(listFuture1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return leaders;
    }


    private List<Leader> getTechnicalInformation(String startDate, String endDate) {

        List<Leader> leaders = new ArrayList<>();
        List<Status> statuses = new ArrayList<>();
        statuses.add(Status.Done);
        statuses.add(Status.Delivered);
        List<Technical> technicals = technicalService.serviceReportByStatusIn(startDate, endDate, statuses);
        Map<String, List<Technical>> collect2 = technicals.stream().collect(Collectors.groupingBy(sales1 -> sales1.getUsername()));
        Iterator<Map.Entry<String, List<Technical>>> iterator2 = collect2.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<String, List<Technical>> next = iterator2.next();
            User oneByUsername = userRepo.findOneByUsername(next.getKey());
            Leader leader = new Leader();
            leader.setUser(oneByUsername);
            leader.setTechnicals(next.getValue());
            leaders.add(leader);
        }
        return leaders;
    }

    private List<Leader> getSalesInformation(String startDate, String endDate) {
        List<Leader> leaders = new ArrayList<>();
        List<Sales> sales = this.sales.salesReport(startDate, endDate);
        Map<String, List<Sales>> collect = sales.stream().collect(Collectors.groupingBy(sales1 -> sales1.getUsername()));
        Iterator<Map.Entry<String, List<Sales>>> iterator = collect.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<Sales>> next = iterator.next();
            User oneByUsername = userRepo.findOneByUsername(next.getKey());
            Leader leader = new Leader();
            leader.setUser(oneByUsername);
            leader.setProduct(next.getValue());
            leaders.add(leader);
        }

        return leaders;
    }


    private List<Leader> getSalesInformationByBrandAndModel(String brand, String model, DateTime startDate, DateTime endDate) {
        List<Leader> leaders = new ArrayList<>();
        List<Sales> sales = this.sales.salesOfMobileByBrandModelDate(brand, model, startDate, endDate);
        Map<String, List<Sales>> collect = sales.stream().collect(Collectors.groupingBy(sales1 -> sales1.getUsername()));
        Iterator<Map.Entry<String, List<Sales>>> iterator = collect.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<Sales>> next = iterator.next();
            User oneByUsername = userRepo.findOneByUsername(next.getKey());
            Leader leader = new Leader();
            leader.setUser(oneByUsername);
            leader.setProduct(next.getValue());
            leaders.add(leader);
        }

        return leaders;
    }
}
