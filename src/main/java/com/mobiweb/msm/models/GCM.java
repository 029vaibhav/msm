package com.mobiweb.msm.models;

import java.util.List;

public class GCM {

    List<String> registration_ids;
    GCMmessage data;

    public List<String> getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(List<String> registration_ids) {
        this.registration_ids = registration_ids;
    }

    public GCMmessage getData() {
        return data;
    }

    public void setData(GCMmessage data) {
        this.data = data;
    }
}
