package com.mobiweb.msm.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Images extends MetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String pathID,path;

    public String getPathID() {
        return pathID;
    }

    public void setPathID(String pathID) {
        this.pathID = pathID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
