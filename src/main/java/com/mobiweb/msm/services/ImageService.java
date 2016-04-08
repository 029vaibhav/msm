package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Images;

public interface ImageService {

    void create(Images Image);

    void update(long id, Images Image);

    Images retrieve(long id);

    Images delete(long id);

    String insertFile(String uploadFile);

}
