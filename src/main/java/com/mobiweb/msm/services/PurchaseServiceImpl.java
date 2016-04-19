package com.mobiweb.msm.services;

import com.mobiweb.msm.exceptions.ImageCantBeProcessed;
import com.mobiweb.msm.models.DealerInfo;
import com.mobiweb.msm.models.ExpenseManager;
import com.mobiweb.msm.models.Payment;
import com.mobiweb.msm.models.Purchase;
import com.mobiweb.msm.repositories.PurchaseRepo;
import com.mobiweb.msm.utils.Constants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    PurchaseRepo purchaseRepo;
    @Autowired
    PaymentService paymentService;
    @Autowired
    ImageService imageService;
    @Autowired
    DealerInfoService dealerInfoService;


    @Override
    public Purchase create(Purchase purchase) {

        try {
            String s = imageService.insertFile(purchase.getImage());
            purchase.setImage(s);
            purchase.setCreated(DateTime.now().withZone(DateTimeZone.UTC));
            purchase.setModified(DateTime.now().withZone(DateTimeZone.UTC));
            Purchase save = purchaseRepo.save(purchase);
            int sum = getPurchaseAmount(purchase.getDealerId());
            updateDealerInfo(sum, purchase.getDealerId());
            return save;
        } catch (ImageCantBeProcessed ImageCantBeProcessed) {
            throw ImageCantBeProcessed;
        }


    }

    @Override
    public void update(Purchase purchase) {

        try {
            purchaseRepo.save(purchase);
            DealerInfo retrieve = dealerInfoService.retrieve(purchase.getDealerId());
            retrieve.setModified(DateTime.now().withZone(DateTimeZone.UTC));
            dealerInfoService.update(retrieve);
        } catch (Exception e) {

        }

    }

    @Override
    public Purchase retrieve(long id) {

        try {
            Purchase Purchase = purchaseRepo.getOne(id);
            return Purchase;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Purchase delete(long id) {

        try {
            Purchase purchase = purchaseRepo.getOne(id);
            purchaseRepo.delete(id);
            int sum = getPurchaseAmount(purchase.getDealerId());
            updateDealerInfo(sum, purchase.getDealerId());
            return purchase;

        } catch (Exception e) {
            throw e;
        }
    }

    private void updateDealerInfo(int sum, Long id) {

        DealerInfo retrieve = dealerInfoService.retrieve(id);
        retrieve.setTotalAmount(sum);
        retrieve.setModified(DateTime.now().withZone(DateTimeZone.UTC));
        dealerInfoService.update(retrieve);
    }

    @Override
    public List<ExpenseManager> getExpenseDetails(String fromDate) {

        List<DealerInfo> dealerInfoList = dealerInfoService.modifiedBetweenDates(fromDate, DateTime.now().toString(Constants.DATE_FORMAT));

        DateTime startDateTime = Constants.getDate(fromDate);

        List<ExpenseManager> expenseManagerList = new ArrayList<>();
        for (DealerInfo dealerInfo : dealerInfoList) {
            List<Payment> paymentList = paymentService.findAllByDealerIdAndCreatedDates(dealerInfo.getServerId(), fromDate, DateTime.now().toString(Constants.DATE_FORMAT));
            List<Purchase> purchaseList = purchaseRepo.findAllByDealerIdAndCreatedBetween(dealerInfo.getServerId(), startDateTime, DateTime.now());
            ExpenseManager expenseManager = new ExpenseManager();
            expenseManager.setDealerInfo(dealerInfo);
            expenseManager.setPaymentList(paymentList);
            expenseManager.setPurchaseList(purchaseList);
            expenseManagerList.add(expenseManager);
        }
        return expenseManagerList;
    }


    public int getPurchaseAmount(Long dealerId) {
        List<Purchase> all = purchaseRepo.findAllByDealerId(dealerId);
        int sum = all.stream().mapToInt(Purchase::getAmount).sum();
        return sum;
    }
}
