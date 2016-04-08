package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Location;
import com.mobiweb.msm.repositories.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepo LocationRepo;


    @Override
    public void create(Location Location) {

        LocationRepo.save(Location);
    }

    @Override
    public void update(long id, Location Location) {

    }

    @Override
    public Location retrieve(long id) {

        try {
            Location Location = LocationRepo.getOne(id);
            return Location;

        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Location delete(long id) {

        try {
            Location Location = LocationRepo.getOne(id);
            LocationRepo.delete(id);
            return Location;

        } catch (Exception e) {
            throw e;
        }
    }
}
