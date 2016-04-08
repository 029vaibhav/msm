package com.mobiweb.msm.services;

import com.mobiweb.msm.models.Location;

public interface LocationService {

    void create(Location Location);

    void update(long id, Location Location);

    Location retrieve(long id);

    Location delete(long id);
}

