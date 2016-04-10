package com.mobiweb.msm.services;

import com.mobiweb.msm.models.DirtyProduct;
import com.mobiweb.msm.models.Sales;
import com.mobiweb.msm.models.enums.ProductType;
import org.joda.time.DateTime;

import java.util.List;

public interface SalesService {

    void create(Sales Sales);

    void update(Sales Sales);

    Sales retrieve(long id);

    Sales delete(long id);

    List<Sales> salesReport(String startDate, String endDate);

    List<Sales> salesReportByBrand(String startDate, String endDate, ProductType productType);

    List<Sales> salesByUser(String startDate, String endDate, String username);

    List<Sales> salesOfMobileByBrandModelDate(String brand, String model, DateTime startDate, DateTime endDate);

    void convertOldSalesToNewSales(List<DirtyProduct> dirtyProducts);

}
