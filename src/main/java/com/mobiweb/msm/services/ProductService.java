package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Product;

import java.util.List;

public interface ProductService {

    Product create(Product Product);

    Product createAccessory(Product Product);


    void update(Product Product);

    Product retrieve(long id);

    Product delete(long id);

    List<Product> getAllProducts();


}
