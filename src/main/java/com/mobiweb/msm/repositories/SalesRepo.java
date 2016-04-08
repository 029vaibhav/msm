package com.mobiweb.msm.repositories;

import com.mobiweb.msm.models.Sales;
import com.mobiweb.msm.models.enums.ProductType;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepo extends JpaRepository<Sales, Long> {


    List<Sales> findAllByCreatedBetween(DateTime created, DateTime created2);
    List<Sales> findAllByCreatedAfterAndCreatedBefore(DateTime created, DateTime created2);

    //    @Query("select b from Sales b " +
//            "where b.created between ?1 and ?2 and b.product_type = ")
    List<Sales> findAllByCreatedBetweenAndProductType(DateTime created, DateTime created2, ProductType productType);

    List<Sales> findAllByImeiAndMobile(String imei, String mobile);

    List<Sales> findAllByUsernameAndCreatedBetween(String username, DateTime created, DateTime created2);

    List<Sales> findAllByBrandAndModelAndProductTypeAndCreatedBetween(String brand, String model, ProductType productType, DateTime created, DateTime created2);


}
