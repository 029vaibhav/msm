package com.mobiweb.msm.repositories;

import com.mobiweb.msm.models.DealerInfo;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealerInfoRepo extends JpaRepository<DealerInfo, Long> {


    DealerInfo findOneByDealerName(String dealerName);

    List<DealerInfo> findAllByModifiedBetween(DateTime startDate,DateTime endDate);
    List<DealerInfo> findAllByModifiedAfter(DateTime startDate);

}
