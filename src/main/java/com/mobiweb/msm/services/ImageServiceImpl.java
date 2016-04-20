package com.mobiweb.msm.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.mobiweb.msm.exceptions.ImageCantBeProcessed;
import com.mobiweb.msm.models.Images;
import com.mobiweb.msm.repositories.ImagesRepo;
import com.mobiweb.msm.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImagesRepo ImagesRepo;

    @Override
    public String insertFile(String multipartFile) {

        AWSCredentials credentials = new BasicAWSCredentials(
                "accessKey",
                "secretKey");
        AmazonS3 s3client = new AmazonS3Client(credentials);

        String fileName = UUID.randomUUID().toString() + ".jpg";
        try {
            PutObjectResult putObjectResult = s3client.putObject(new PutObjectRequest(Constants.BUCKET_NAME, fileName, Constants.convertString(multipartFile))
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AmazonServiceException ase) {
            throw new ImageCantBeProcessed("Please select different Image");
        } catch (AmazonClientException ace) {
            throw new ImageCantBeProcessed("Cant connect to image server try again");
        }
        return "https://s3-ap-southeast-1.amazonaws.com/msm-images/" + fileName;
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
