package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Images;
import com.mobiweb.msm.repositories.ImagesRepo;
import com.mobiweb.msm.utils.Constants;
import com.mobiweb.msm.utils.MyClass;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImagesRepo ImagesRepo;
    @Autowired
    MyClass myClass;


    @Override
    public String insertFile(String multipartFile) {
        try {
            return myClass.insertFile(DateTime.now().toString(Constants.DATE_FORMAT), "abc", null, "image/jpeg", multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Images Images) {

        ImagesRepo.save(Images);
    }

    @Override
    public void update(long id, Images Images) {

    }

    @Override
    public Images retrieve(long id) {

        try {
            Images Images = ImagesRepo.getOne(id);
            return Images;

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Images delete(long id) {

        try {
            Images Images = ImagesRepo.getOne(id);
            ImagesRepo.delete(id);
            return Images;

        } catch (Exception e) {
            throw e;
        }
    }
}
