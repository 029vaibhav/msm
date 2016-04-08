package com.mobiweb.msm.repositories;

import com.mobiweb.msm.models.Product;
import com.mobiweb.msm.models.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Product findOneByBrandAndModelAndType(String brand, String mobile, ProductType productType);
}
