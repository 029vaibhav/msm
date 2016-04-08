package com.mobiweb.msm.services;

import com.mobiweb.msm.models.DealerInfo;

import java.util.List;

public interface DealerInfoService {

    DealerInfo create(DealerInfo DealerInfo);

    void update(DealerInfo DealerInfo);

    DealerInfo retrieve(long id);

    DealerInfo delete(long id);

    List<DealerInfo> modifiedBetweenDates(String startDate,String endDate);
    List<DealerInfo> modifiedAfter(String startDate);


}
