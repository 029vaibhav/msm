package com.mobiweb.msm.models;

import java.util.List;

public class Leader {

    User user;
    List<Sales> product;
    List<Technical> technicals;

    public List<Technical> getTechnicals() {
        return technicals;
    }

    public void setTechnicals(List<Technical> technicals) {
        this.technicals = technicals;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Sales> getProduct() {
        return product;
    }

    public void setProduct(List<Sales> product) {
        this.product = product;
    }
}
