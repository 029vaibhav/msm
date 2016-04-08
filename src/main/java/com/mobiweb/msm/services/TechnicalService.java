package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Sales;
import com.mobiweb.msm.models.Technical;
import com.mobiweb.msm.models.enums.Status;

import java.util.List;

public interface TechnicalService {


    void create(Technical Technical);

    void update(Technical Technical);

    Technical retrieve(long id);

    Technical delete(long id);

    List<Technical> serviceReport(String startDate, String endDate);

    List<Technical> serviceReportByStatusIn(String startDate, String endDate, List<Status> statuses);

    List<Sales> serviceReportByBrand(String startDate, String endDate);

    List<Technical> ListForTechnicians();

    List<Technical> servicesByUser(String startDate, String endDate, String username);

}
