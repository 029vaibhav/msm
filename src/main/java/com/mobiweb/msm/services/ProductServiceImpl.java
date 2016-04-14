package com.mobiweb.msm.services;

import com.mobiweb.msm.exceptions.DuplicateProduct;
import com.mobiweb.msm.exceptions.error.ErrorMessage;
import com.mobiweb.msm.models.Product;
import com.mobiweb.msm.repositories.ProductRepo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;


    @Override
    public void create(Product product) {

        Product oneByBrandAndModelAndType = productRepo.findOneByBrandAndModelAndType(product.getBrand(), product.getModel(), product.getType());
        if (oneByBrandAndModelAndType == null) {
            product.setCreated(DateTime.now().withZone(DateTimeZone.UTC));
            product.setModified(DateTime.now().withZone(DateTimeZone.UTC));
            productRepo.save(product);
        } else {
            throw new DuplicateProduct(ErrorMessage.DUPLICATE_PRODUCT);
        }
    }

    @Override
    public void update(Product product) {
        product.setModified(DateTime.now().withZone(DateTimeZone.UTC));
        productRepo.save(product);
    }

    @Override
    public Product retrieve(long id) {

        try {
            Product Product = productRepo.getOne(id);
            return Product;

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Product delete(long id) {

        try {
            Product Product = productRepo.getOne(id);
            productRepo.delete(id);
            return Product;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }


}
