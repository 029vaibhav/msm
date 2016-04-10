package com.mobiweb.msm.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@JsonIgnoreProperties()
@EnableAutoConfiguration
public class DealerInfo extends MetaData{

    String dealerName;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serverId;
    // purchase amount
    private long totalAmount;
    private long payedAmount;

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(long payedAmount) {
        this.payedAmount = payedAmount;
    }

    public Long getServerID() {
        return serverId;
    }

    public void setServerID(Long serverID) {
        this.serverId = serverID;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
}
