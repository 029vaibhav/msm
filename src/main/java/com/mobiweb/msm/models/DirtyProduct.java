package com.mobiweb.msm.models;

public class DirtyProduct {

    private String id;

    private String price;

    private String brandModel;

    private String created;

    private String status;

    private String brand;

    private String userName;

    private String deliveryDate;

    private String problem;

    private String resolution;

    private String jobNo;

    private String place;

    private String modifiedDate;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getBrandModel ()
    {
        return brandModel;
    }

    public void setBrandModel (String brandModel)
    {
        this.brandModel = brandModel;
    }

    public String getCreated ()
    {
        return created;
    }

    public void setCreated (String created)
    {
        this.created = created;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getBrand ()
    {
        return brand;
    }

    public void setBrand (String brand)
    {
        this.brand = brand;
    }

    public String getUserName ()
    {
        return userName;
    }

    public void setUserName (String userName)
    {
        this.userName = userName;
    }

    public String getDeliveryDate ()
    {
        return deliveryDate;
    }

    public void setDeliveryDate (String deliveryDate)
    {
        this.deliveryDate = deliveryDate;
    }

    public String getProblem ()
    {
        return problem;
    }

    public void setProblem (String problem)
    {
        this.problem = problem;
    }

    public String getResolution ()
    {
        return resolution;
    }

    public void setResolution (String resolution)
    {
        this.resolution = resolution;
    }

    public String getJobNo ()
    {
        return jobNo;
    }

    public void setJobNo (String jobNo)
    {
        this.jobNo = jobNo;
    }

    public String getPlace ()
    {
        return place;
    }

    public void setPlace (String place)
    {
        this.place = place;
    }

    public String getModifiedDate ()
    {
        return modifiedDate;
    }

    public void setModifiedDate (String modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", price = "+price+", brandModel = "+brandModel+", created = "+created+", status = "+status+", brand = "+brand+", userName = "+userName+", deliveryDate = "+deliveryDate+", problem = "+problem+", resolution = "+resolution+", jobNo = "+jobNo+", place = "+place+", modifiedDate = "+modifiedDate+"]";
    }}
