package com.mobiweb.msm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobiweb.msm.models.enums.ProductType;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product extends MetaData {

    String brand, model, sim, screenSize, displayType, os, iMemory, eMemory, fCamera, bCamera, wlan, bluetooth, nfc, infrared, radio, battery;
    int price;
    @Enumerated(EnumType.STRING)
    ProductType type;
    String accessoryType;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getiMemory() {
        return iMemory;
    }

    public void setiMemory(String iMemory) {
        this.iMemory = iMemory;
    }

    public String geteMemory() {
        return eMemory;
    }

    public void seteMemory(String eMemory) {
        this.eMemory = eMemory;
    }

    public String getfCamera() {
        return fCamera;
    }

    public void setfCamera(String fCamera) {
        this.fCamera = fCamera;
    }

    public String getbCamera() {
        return bCamera;
    }

    public void setbCamera(String bCamera) {
        this.bCamera = bCamera;
    }

    public String getWlan() {
        return wlan;
    }

    public void setWlan(String wlan) {
        this.wlan = wlan;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getNfc() {
        return nfc;
    }

    public void setNfc(String nfc) {
        this.nfc = nfc;
    }

    public String getInfrared() {
        return infrared;
    }

    public void setInfrared(String infrared) {
        this.infrared = infrared;
    }

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getAccessoryType() {
        return accessoryType;
    }

    public void setAccessoryType(String accessoryType) {
        this.accessoryType = accessoryType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
