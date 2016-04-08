package com.mobiweb.msm.services;

import com.mobiweb.msm.models.DealerInfo;
import com.mobiweb.msm.repositories.DealerInfoRepo;
import com.mobiweb.msm.utils.Constants;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealerInfoServiceImpl implements DealerInfoService {

    private final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    DealerInfoRepo dealerInfoRepo;

    @Override
    public DealerInfo create(DealerInfo dealerInfo) {

        try {
            DealerInfo oneByDealerName = dealerInfoRepo.findOneByDealerName(dealerInfo.getDealerName());
            if (oneByDealerName == null) {
                dealerInfo.setCreated(DateTime.now().withZone(DateTimeZone.UTC));
                return dealerInfoRepo.save(dealerInfo);
            } else {
                log.error("dealer doesnt exists " + dealerInfo.getDealerName());
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void update(DealerInfo dealerInfo) {

        try {
            dealerInfoRepo.save(dealerInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
        }


    }

    @Override
    public DealerInfo retrieve(long id) {

        try {
            DealerInfo DealerInfo = dealerInfoRepo.getOne(id);
            return DealerInfo;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

    }

    @Override
    public DealerInfo delete(long id) {

        try {
            DealerInfo DealerInfo = dealerInfoRepo.getOne(id);
            dealerInfoRepo.delete(id);
            return DealerInfo;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<DealerInfo> modifiedBetweenDates(String startDate, String endDate) {

        DateTime startDateTime = Constants.getDate(startDate);
        DateTime endDateTime = Constants.getDate(endDate);

        List<DealerInfo> allByModifiedBetween = dealerInfoRepo.findAllByModifiedBetween(startDateTime, endDateTime);
        return allByModifiedBetween;
    }

    @Override
    public List<DealerInfo> modifiedAfter(String startDate) {

        DateTime startDateTime = Constants.getDate(startDate);
        List<DealerInfo> allByModifiedBetween = dealerInfoRepo.findAllByModifiedAfter(startDateTime);
        return allByModifiedBetween;
    }
}
