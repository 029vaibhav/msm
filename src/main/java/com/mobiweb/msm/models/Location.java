package com.mobiweb.msm.models;

import com.mobiweb.msm.models.enums.Role;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Location extends MetaData{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    BigDecimal latitude,longitude,radius;
    @Enumerated(EnumType.STRING)
    Role role;

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getRadius() {
        return radius;
    }

    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getLatitude() {

        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
}
