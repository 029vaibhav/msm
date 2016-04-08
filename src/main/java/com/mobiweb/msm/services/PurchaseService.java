package com.mobiweb.msm.services;

import com.mobiweb.msm.models.ExpenseManager;
import com.mobiweb.msm.models.Purchase;

import java.util.List;

public interface PurchaseService {

    Purchase create(Purchase Purchase);

    void update( Purchase Purchase);

    Purchase retrieve(long id);

    Purchase delete(long id);

    List<ExpenseManager> getExpenseDetails(String fromDate);
}
