package com.mobiweb.msm.repositories;

import com.mobiweb.msm.models.Purchase;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Long> {

    List<Purchase> findAllByDealerIdAndCreatedBetween(Long dealerId, DateTime from, DateTime to);

    List<Purchase> findAllByCreatedBetween(DateTime from, DateTime to);

    List<Purchase> findAllByDealerId(Long dealerID);

    List<Purchase> findAllByDealerIdAndCreatedAfter(Long dealerId, DateTime from);



}
