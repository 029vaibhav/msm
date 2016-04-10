package com.mobiweb.msm.services;

import com.mobiweb.msm.exceptions.SameIMEI;
import com.mobiweb.msm.exceptions.error.ErrorMessage;
import com.mobiweb.msm.models.DirtyProduct;
import com.mobiweb.msm.models.Sales;
import com.mobiweb.msm.models.Technical;
import com.mobiweb.msm.models.User;
import com.mobiweb.msm.models.enums.ProductType;
import com.mobiweb.msm.models.enums.Role;
import com.mobiweb.msm.models.enums.Status;
import com.mobiweb.msm.repositories.SalesRepo;
import com.mobiweb.msm.repositories.TechnicalRepo;
import com.mobiweb.msm.utils.Constants;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    SalesRepo salesRepo;
    @Autowired
    UserService userService;
    @Autowired
    TechnicalRepo technicalService;


    @Override
    public void create(Sales sales) {
        try {
            List<Sales> allByImeiAndMobile = salesRepo.findAllByImeiAndMobile(sales.getImei(), sales.getMobile());
            if (allByImeiAndMobile == null || allByImeiAndMobile.size() == 0) {
                sales.setCreated(DateTime.now());
                sales.setModified(DateTime.now());
                Sales sales1 = salesRepo.save(sales);
                sendNotification(sales1);
            } else {
                throw new SameIMEI(ErrorMessage.SAME_IMEI);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private void sendNotification(Sales sales1) {
        List<User> allUserByRole = userService.getAllUserByRole(Role.Receptionist);
        List<String> registrationIDS = new ArrayList<>();
        allUserByRole.stream().forEach(user -> {
            if (user.getRegId() != null)
                registrationIDS.add(user.getRegId());
        });
        if (registrationIDS.size() > 0) {
            Constants.SendMessageToGCM(registrationIDS, sales1);
        }
    }

    @Override
    public void update(Sales sales) {

    }

    @Override
    public Sales retrieve(long id) {

        try {
            Sales Sales = salesRepo.getOne(id);
            return Sales;

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Sales delete(long id) {

        try {
            Sales Sales = salesRepo.getOne(id);
            salesRepo.delete(id);
            return Sales;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Sales> salesReport(String startDate, String endDate) {
        DateTime startDateTime = Constants.getShortDate(startDate);
        DateTime endDateDateTime = Constants.getShortDate(endDate);
        List<Sales> byDatesBetween = salesRepo.findAllByCreatedBetween(startDateTime, endDateDateTime);
        return byDatesBetween;
    }

    @Override
    public List<Sales> salesReportByBrand(String startDate, String endDate, ProductType productType) {
        DateTime startDateTime = Constants.getShortDate(startDate);
        DateTime endDateDateTime = Constants.getShortDate(endDate);
        List<Sales> sales = salesRepo.findAllByCreatedBetweenAndProductType(startDateTime, endDateDateTime, productType);
        List<Sales> returnSales = new ArrayList<>();
        Map<String, List<Sales>> collect1 = sales.stream().collect(Collectors.groupingBy(sales1 -> sales1.getBrand()));
        Iterator<Map.Entry<String, List<Sales>>> iterator = collect1.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<Sales>> next = iterator.next();

            Sales sales2 = new Sales();
            int price = 0, quantity = 0;
            for (Sales sales1 : next.getValue()) {
                price += sales1.getPrice();
                quantity += sales1.getQuantity();

            }
            sales2.setBrand(next.getKey());
            sales2.setQuantity(quantity);
            sales2.setPrice(price);
            returnSales.add(sales2);
        }
        return returnSales;
    }

    @Override
    public List<Sales> salesByUser(String startDate, String endDate, String username) {


        DateTime startDateTime = Constants.getShortDate(startDate);
        DateTime endDateDateTime = Constants.getShortDate(endDate);
        return salesRepo.findAllByUsernameAndCreatedBetween(username, startDateTime, endDateDateTime);
    }

    @Override
    public List<Sales> salesOfMobileByBrandModelDate(String brand, String model, DateTime startDate, DateTime endDate) {
        List<Sales> allByCreatedBetween = salesRepo.findAllByCreatedBetween(startDate, endDate);
        List<Sales> result = salesRepo.findAllByBrandAndModelAndProductTypeAndCreatedBetween(brand, model, ProductType.Mobile, startDate, endDate);
        return result;
    }

    @Override
    public void convertOldSalesToNewSales(List<DirtyProduct> dirtyProducts) {

        System.out.println("dirty " + dirtyProducts.size());
        dirtyProducts.stream().forEach(dirtyProduct -> {

            Technical sales = new Technical();
            String[] split = dirtyProduct.getBrandModel().split("-");
            sales.setBrand(split[0]);
            sales.setModel(split[1]);
            sales.setCreated(Constants.getShortDate(dirtyProduct.getCreated()));
            sales.setModified(Constants.getDate(dirtyProduct.getModifiedDate()));
            sales.setUsername(dirtyProduct.getUserName());
            sales.setJobNo(dirtyProduct.getJobNo());
            sales.setPrice(Integer.parseInt(dirtyProduct.getPrice()));
            sales.setProblem(dirtyProduct.getProblem());
            sales.setPlace(dirtyProduct.getPlace());
            sales.setStatus(Status.valueOf(dirtyProduct.getStatus()));
            sales.setResolution(dirtyProduct.getResolution());
            technicalService.save(sales);

        });

    }


}
