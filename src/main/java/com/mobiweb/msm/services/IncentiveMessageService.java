package com.mobiweb.msm.services;

import com.mobiweb.msm.models.IncentiveMessage;
import com.mobiweb.msm.models.Sales;

import java.util.List;

public interface IncentiveMessageService {

    void create(IncentiveMessage IncentiveMessage);

    void update(IncentiveMessage IncentiveMessage);

    IncentiveMessage retrieve(long id);

    IncentiveMessage delete(long id);

    List<IncentiveMessage> getAll();

    List<Sales> getIncentiveQualifiedUsers(long incentiveID);

}
