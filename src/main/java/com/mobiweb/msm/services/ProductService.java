package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Product;

import java.util.List;

public interface ProductService {

    void create(Product Product);

    void update(Product Product);

    Product retrieve(long id);

    Product delete(long id);

    List<Product> getAllProducts();



}
